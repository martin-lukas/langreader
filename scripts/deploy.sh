#!/bin/bash
# This script builds and sends the project to the AWS server, and also updates the
# PostgreSQL database there. After running this script, you can SSH to the server and
# in the home dir, run the script ~/langreader/run_server.sh
## this is not a part of the deploy.sh script, because if Intellij IDEA crashes, while this
## script is still running, the remote ports and DB connections stay open, and further running
## this script fails because the DB is protected from being overriden (still in use). Also the
## 80 port is still occupied. Only restarting the server helps.

# Get to the project root
cd ..

# Dump the langreader database
pg_dump langreader > db_dump

# Send the dump to server
scp -i /home/martin/.ssh/amazon-ec2-server.pem \
/home/martin/IdeaProjects/langreader/db_dump \
ubuntu@ec2-18-185-128-105.eu-central-1.compute.amazonaws.com:~/langreader/

# Build frontend
cd frontend/ || exit
vue build

# Build backend + frontend together
cd ../backend/ || exit
mvn clean package

# Send the built jar to server
scp -i /home/martin/.ssh/amazon-ec2-server.pem \
/home/martin/IdeaProjects/langreader/backend/target/backend-0.0.1-SNAPSHOT.jar \
ubuntu@ec2-18-185-128-105.eu-central-1.compute.amazonaws.com:~/langreader/

# Prepare remote DB
cd ..
ssh -i /home/martin/.ssh/amazon-ec2-server.pem \
ubuntu@ec2-18-185-128-105.eu-central-1.compute.amazonaws.com \
'bash -s' < ./scripts/remote_db_prepare.sh

# Remove the local DB dump
rm db_dump


