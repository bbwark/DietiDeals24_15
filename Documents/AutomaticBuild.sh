#!/bin/bash

# Funzione di logging potenziata con informazioni di debug
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a automatic_build.log
}

# Pulizia log precedenti
> automatic_build.log
log "--- Start Automatic Build process ---"

# Determina il percorso assoluto dello script e della directory corrente
SCRIPT_PATH="$(readlink -f "$0")"
SCRIPT_DIR="$(dirname "$SCRIPT_PATH")"

# Nome del file di configurazione
CONFIG_FILE="BuildConfig.cfg"

# Verifica che il file di configurazione esista
if [[ ! -f "$SCRIPT_DIR/$CONFIG_FILE" ]]; then
    log "ERROR: Configuration file '$CONFIG_FILE' doesn't exists."
    exit 1
fi
log "Configuration file '$CONFIG_FILE' successfully loaded."

# Caricamento delle variabili dal file di configurazione
source "$SCRIPT_DIR/$CONFIG_FILE"

# Costruisci il percorso completo della chiave privata
PRIVATE_KEY_PATH="$SCRIPT_DIR/$private_key"

# Verifica che tutte le variabili siano impostate
if [[ -z "$private_key" || -z "$distribution" || -z "$ip_address" || -z "$folder_name" ]]; then
    log "ERROR: One or more variables in configuration file are missing."
    exit 1
fi
log "Configuration variables successfully verified."

# Verifica esistenza chiave privata
if [[ ! -f "$PRIVATE_KEY_PATH" ]]; then
    log "ERROR: Private key not found in $PRIVATE_KEY_PATH"
    exit 1
fi
log "Private key found in $PRIVATE_KEY_PATH"

# Modifica per gestire correttamente il percorso della chiave con sudo
scp_transfer() {
    local source_file="$1"
    local destination="$2"
   
    log "Sending $source_file to remote server..."
   
    # Usa sudo -E per preservare le variabili d'ambiente
    sudo -E scp -o StrictHostKeyChecking=no -i "$PRIVATE_KEY_PATH" "$source_file" "$destination" || {
        log "ERROR: Impossible to send $source_file"
        return 1
    }
}

# Trasferimento RemoteScript.sh
scp_transfer "RemoteScript.sh" "$distribution@$ip_address:/home/$distribution" || exit 1

# Trasferimento file di configurazione
scp_transfer "$CONFIG_FILE" "$distribution@$ip_address:/home/$distribution" || exit 1

# Creazione pacchetto
log "Zipping package..."
zip -r "${folder_name}.zip" "../back/${folder_name}.zip" || {
    log "ERROR: during zipping files"
    exit 1
}
log "ZIP package ${folder_name}.zip successfully created."


# Creazione file SQL
SQL_FILE="dietidealsdatabase_reset.sql"
log "Creating file SQL to reset database..."
cat > "$SQL_FILE" <<EOF
\c postgres
DROP DATABASE IF EXISTS dietidealsdatabase;
CREATE DATABASE dietidealsdatabase;
EOF

# Trasferimento pacchetto ZIP
scp_transfer "../back/${folder_name}.zip" "$distribution@$ip_address:/home/$distribution" || exit 1

# Trasferimento script SQL
scp_transfer "$SQL_FILE" "$distribution@$ip_address:/home/$distribution" || exit 1

# Esecuzione script remoto
log "Starting RemoteScript.sh on remote server..."
sudo -E ssh -o StrictHostKeyChecking=no -i "$PRIVATE_KEY_PATH" "$distribution@$ip_address" "bash /home/$distribution/RemoteScript.sh" || {
    log "ERROR: Failed executing RemoteScript.sh on remote server"
    exit 1
}

log "--- End Automatic Build process ---"