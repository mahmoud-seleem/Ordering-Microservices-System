#!/bin/sh
set -e

eureka_host="$1"
app_name="$2"
shift 2
cmd="$@"

echo "Waiting for $app_name to register in Eureka at $eureka_host..."

while true; do
  # Check if the app is registered by requesting Eureka REST API
  status_code=$(curl -s -o /dev/null -w "%{http_code}" "$eureka_host/eureka/apps/$app_name")

  if [ "$status_code" = "200" ]; then
    echo "$app_name is registered in Eureka."
    break
  else
    echo "Waiting for $app_name to register in Eureka..."
    sleep 5
  fi
done

exec $cmd