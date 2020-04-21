#!/bin/bash

# Drop current DB
sudo -i -u postgres dropdb langreader

# Create fresh DB
sudo -i -u postgres createdb --owner="ubuntu" langreader

# Initialize DB
psql langreader < ~/langreader/db_dump