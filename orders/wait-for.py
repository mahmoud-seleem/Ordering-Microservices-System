#!/usr/bin/env python3
import sys
import time
import subprocess
import requests

def wait_for_eureka(eureka_host, app_name):
    url = f"{eureka_host}/eureka/apps/{app_name}"
    print(f"Waiting for {app_name} to register in Eureka at {url}...")
    while True:
        try:
            response = requests.get(url, timeout=5)
            if response.status_code == 200:
                print(f"{app_name} is registered in Eureka.")
                break
            else:
                print(f"Waiting for {app_name}... (Eureka HTTP {response.status_code})")
        except requests.RequestException as e:
            print(f"Error connecting to Eureka: {e}")
        time.sleep(5)

def main():
    if len(sys.argv) < 4:
        print("Usage: wait_for_registration.py <eureka_host> <app_name> <command...>")
        sys.exit(1)

    eureka_host = sys.argv[1]
    app_name = sys.argv[2]
    command = sys.argv[3:]

    wait_for_eureka(eureka_host, app_name)

    print(f"Registration confirmed. Executing: {' '.join(command)}")
    subprocess.run(command)

if __name__ == "__main__":
    main()