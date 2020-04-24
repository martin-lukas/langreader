#!/bin/bash

# Build frontend
cd ../frontend/ || exit
vue build

# Build backend + frontend together
cd ../backend/ || exit
mvn clean package
