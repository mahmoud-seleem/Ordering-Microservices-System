#!/bin/sh
set -e

eureka_host="$1"
app_name="$2"
app_health_url="$3" # NEW ARGUMENT: The actual HTTP URL to the application's health endpoint
shift 3             # Shift 3 arguments now, as we have a new one
cmd="$@"

echo "Waiting for $app_name to register in Eureka at $eureka_host..."

# --- Stage 1: Wait for Eureka registration ---
while true; do
  # Check if the app is registered by requesting Eureka REST API
  status_code=$(curl -s -o /dev/null -w "%{http_code}" "$eureka_host/eureka/apps/$app_name")

  if [ "$status_code" = "200" ]; then
    echo "$app_name is registered in Eureka."
    break
  else
    echo "Waiting for $app_name to register in Eureka (HTTP Status: $status_code)..."
    sleep 5
  fi
done
exec $cmd