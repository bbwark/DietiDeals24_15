log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $*" | tee -a remote_script.log
}

CONFIG_FILE="BuildConfig.cfg"

if [[ ! -f "$CONFIG_FILE" ]]; then
    log "ERROR: Configuration file '$CONFIG_FILE' doesn't exists."
    exit 1
fi

source "$CONFIG_FILE"

if [[ -z "$folder_name" || -z "$container_name" || -z "$image_name" || -z "$network_name" || -z "$postgres_username" || -z "$postgres_password" || -z "$distribution" ]]; then
    log "ERROR: One or more variables in configuration file are missing."
    exit 1
fi

> "/home/$distribution/remote_script.log"

log "--- Start Deploy process ---"

log "Removing existing $folder_name..."
rm -rf "$folder_name"

log "Unzipping package ${folder_name}.zip..."
unzip "${folder_name}.zip" || {
    log "ERROR: Impossible to extract ZIP package"
    exit 1
}

log "Stopping Docker container $container_name..."
sudo docker stop "$container_name" || log "WARNING: Impossible to stop container"

log "Pulizia container Docker..."
sudo docker container prune -f

log "Removing Docker image $image_name..."
sudo docker rmi -f "$image_name" || log "WARNING: Impossible to remove image"

cd "$folder_name" || { 
    log "ERROR: Impossible to access directory $folder_name"
    exit 1
}

log "Modifying application.properties..."
FILE_TO_MODIFY="src/main/resources/application.properties"
sed -i '6s/^/#/' "$FILE_TO_MODIFY"
sed -i '7s/^#//' "$FILE_TO_MODIFY"

log "Building project with Maven..."
mvn clean install -DskipTests || {
    log "ERROR: Project build failed"
    exit 1
}

log "Creating Docker image $image_name..."
sudo docker build -t "$image_name" . || {
    log "ERROR: Creating Docker image failed"
    exit 1
}

cd .. || exit


databaseVersion=14
outputPostgresImage=$(sudo docker images -q "postgres:"$databaseVersion"")
if [ ! -n "$outputPostgresImage" ]; then
    log "Pull PostgreSQL "$databaseVersion" image..."
    sudo docker pull "postgres:"$databaseVersion"" || log "WARNING: Pull PostgreSQL image failed"
fi

outputNetwork=$(sudo docker network ls --filter name="^${network_name}$" -q)
if [ ! -n "$outputNetwork" ]; then
    log "Creating Docker network $network_name..."
    sudo docker network create "$network_name" || {
        log "ERROR: Creating Docker network failed"
        exit 1
    }
fi

outputPostgresContainer=$(sudo docker ps --filter "name=^dietideals_ps_database" --filter "status=running" -q)
if [ ! -n "$outputPostgresContainer" ]; then
    log "Starting PostgreSQL container..."
    sudo docker run --network="$network_name" --name dietideals_ps_database \
        -e POSTGRES_USER="$postgres_username" \
        -e POSTGRES_PASSWORD="$postgres_password" \
        -e POSTGRES_DB=postgres \
        -p 5432:5432 -d postgres:"$databaseVersion" || {
        log "ERROR: Starting PostgreSQL container failed"
        exit 1
    }
fi

log "Copying SQL script to reset database..."
sudo docker cp "/home/$distribution/dietidealsdatabase_reset.sql" dietideals_ps_database:/dietidealsdatabase_reset.sql || {
    log "ERROR: Copying SQL script failed"
}

log "Executing SQL script to reset database..."
sudo docker exec -i dietideals_ps_database psql -U postgres -f /dietidealsdatabase_reset.sql || {
    log "ERROR: Executing SQL script failed"
}

log "Starting application container $image_name..."
sudo docker run --network="$network_name" --name="$image_name" -p8181:8181 -d "$image_name":latest || {
    log "ERROR: Starting application container failed"
    exit 1
}

rm -rf "$folder_name"
rm "${folder_name}.zip"
rm "dietidealsdatabase_reset.sql"
rm "$CONFIG_FILE"

log "--- End Deploy process ---"

rm "$0"