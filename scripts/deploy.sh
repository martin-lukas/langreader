#!/bin/bash

# Dump the langreader database
pg_dump langreader > db_dump

# Send the dump to server
scp -i /home/martin/.ssh/amazon-ec2-server.pem \
       db_dump \
       ubuntu@18.185.128.105:~/langreader/

# Build frontend + backend
./build.sh

# Send the built jar to server
scp -i /home/martin/.ssh/amazon-ec2-server.pem \
       ../backend/target/backend-0.0.1-SNAPSHOT.jar \
       ubuntu@18.185.128.105:~/langreader/

# Prepare remote DB
ssh -i /home/martin/.ssh/amazon-ec2-server.pem \
       ubuntu@18.185.128.105 \
       'bash -s' < ./remote-run.sh

# Remove the local DB dump
rm db_dump


