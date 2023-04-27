#!/bin/sh
set -e

# Start by running the setup script
# python setup.py &

# Monitor for changes to IPv4.csv and IPv6.csv files
#/setup/IPv4_Elastic.csv /setup/IPv6_Elastic.csv /setup/Greynoise_IPv4.json /setup/Greynoise_IPv6.json
# Monitor for changes to files in /setup directory
while true; do
  filename=$(inotifywait -e close_write /setup | awk '{print $3}')
  case $filename in
    "IPv4_Elastic.csv")
      python update_ip2l_ipv4.py &
      ;;
    "IPv6_Elastic.csv")
      python update_ip2l_ipv6.py &
      ;;
    "Greynoise_IPv4.json")
      python update_greynoise_ipv4.py &
      ;;
    "Greynoise_IPv6.json")
      python update_greynoise_ipv6.py &
      ;;
    *)
      echo "Unknown file: $filename"
      ;;
  esac
done