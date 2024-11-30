#!/bin/bash

# Logging with debug information
log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a automatic_build.log
}

# Cleaning log file
> automatic_build.log
log "--- Start Automatic Build process ---"

SCRIPT_PATH="$(readlink -f "$0")"
SCRIPT_DIR="$(dirname "$SCRIPT_PATH")"

CONFIG_FILE="BuildConfig.cfg"

if [[ ! -f "$SCRIPT_DIR/$CONFIG_FILE" ]]; then
    log "ERROR: Configuration file '$CONFIG_FILE' doesn't exists."
    exit 1
fi
log "Configuration file '$CONFIG_FILE' successfully loaded."

source "$SCRIPT_DIR/$CONFIG_FILE"

PRIVATE_KEY_PATH="$SCRIPT_DIR/$private_key"

if [[ -z "$private_key" || -z "$distribution" || -z "$ip_address" || -z "$folder_name" ]]; then
    log "ERROR: One or more variables in configuration file are missing."
    exit 1
fi
log "Configuration variables successfully verified."

if [[ ! -f "$PRIVATE_KEY_PATH" ]]; then
    log "ERROR: Private key not found in $PRIVATE_KEY_PATH"
    exit 1
fi
log "Private key found in $PRIVATE_KEY_PATH"

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

scp_transfer "RemoteScript.sh" "$distribution@$ip_address:/home/$distribution" || exit 1

scp_transfer "$CONFIG_FILE" "$distribution@$ip_address:/home/$distribution" || exit 1

log "Zipping package..."
cd ../back || {
    log "ERROR: Impossible to access directory ../back"
    exit 1
}
zip -r "${folder_name}.zip" "${folder_name}" || {
    log "ERROR: during zipping files"
    exit 1
}
cd ../Documents || {
    log "ERROR: Impossible to access directory ../Documents"
    exit 1
}
log "ZIP package ${folder_name}.zip successfully created."


SQL_FILE="dietidealsdatabase_reset.sql"
log "Creating file SQL to reset database..."
cat > "$SQL_FILE" <<EOF
\c postgres
DROP DATABASE IF EXISTS dietidealsdatabase;
CREATE DATABASE dietidealsdatabase;
EOF

scp_transfer "../back/${folder_name}.zip" "$distribution@$ip_address:/home/$distribution" || exit 1
rm "../back/${folder_name}.zip"

scp_transfer "$SQL_FILE" "$distribution@$ip_address:/home/$distribution" || exit 1
rm "$SQL_FILE"

log "Starting RemoteScript.sh on remote server..."
sudo -E ssh -o StrictHostKeyChecking=no -i "$PRIVATE_KEY_PATH" "$distribution@$ip_address" "bash /home/$distribution/RemoteScript.sh" || {
    log "ERROR: Failed executing RemoteScript.sh on remote server"
    exit 1
}

log "--- End Automatic Build process ---"