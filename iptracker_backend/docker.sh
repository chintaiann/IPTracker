#!/bin/sh

# Check if the volume is mounted
# while [ ! -d /usr/share/elasticsearch/config/certs/ca/ca.crt ]; do
#   echo "Waiting for volume to be mounted..."
#   sleep 1
# done
echo "Volume has been mounted."
cd /opt/java/openjdk/lib/security
# Run keytool command
keytool -noprompt -import -trustcacerts -alias mdecert -file /usr/share/elasticsearch/config/certs/ca/ca.crt -keystore cacerts -keypass changeit -storepass changeit

# Change working directory
# cd /backend

# Copy files
# cp -R .mvn/ .mvn
# cp mvnw pom.xml ./
# cp -R src/ ./src
# Run mvnw command
cd ~
cd /backend
echo "The current working directory is: `pwd`"
echo "Listing files in the current directory:"
ls

./mvnw dependency:go-offline
./mvnw spring-boot:run