#!/bin/bash
set -e # Exit immediately if a command exits with a non-zero status.

echo "Starting Maven clean package for all services..."

# Define an array of your service directories
# Make sure these names exactly match your folder names
services=(
    "orders"
    "Inventory-MicroService"
    "Api-gateway" # Adjust if your API Gateway folder name is different
    "configuration-server"
    "service-registry"
)

# Loop through each service and build its JAR
for service_dir in "${services[@]}"; do
    if [ -d "$service_dir" ]; then
        echo "--------------------------------------------------------"
        echo "Building $service_dir..."
        echo "--------------------------------------------------------"
        (cd "$service_dir" && mvn clean package -DskipTests) # Run command in subshell
        echo "Finished building $service_dir."
    else
        echo "ERROR: Directory '$service_dir' not found. Skipping."
        exit 1 # Exit if a required service directory is missing
    fi
done

echo "========================================================"
echo "All Maven packages built successfully."
echo "========================================================"