#!/bin/sh

set -e

while [ ! -d /special/certs ]; do
    echo "Waiting for volume to be mounted..."
    sleep 10
done

echo "Volume mounted!"
exec "$@"