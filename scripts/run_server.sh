#!/bin/bash

sudo java -jar \
	-Dserver.port=80 \
	-Dspring.datasource.username="ubuntu" \
	-Dspring.datasource.password="ubuntu" \
	backend-0.0.1-SNAPSHOT.jar
