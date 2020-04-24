#!/bin/bash

cd langreader/

sudo kill $(sudo lsof -i:80 -t)

# Stop the running server

# Drop current DB
sudo -i -u postgres dropdb langreader

# Create fresh DB
sudo -i -u postgres createdb --owner="ubuntu" langreader

# Initialize DB
psql langreader < db_dump

# Remove the backup
rm db_dump

# Run the server in the background
nohup sudo java -jar \
        -Dserver.port=80 \
        -Dspring.datasource.username="ubuntu" \
        -Dspring.datasource.password="ubuntu" \
        backend-0.0.1-SNAPSHOT.jar > server.out 2> server.err &