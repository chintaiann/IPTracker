#!/bin/sh
set -e
export PYTHONUNBUFFERED=1
# Start by running the setup script
python setup.py
mkdir -p "/setup/GreynoiseIPv4"
mkdir -p "/setup/GreynoiseIPv6"
mkdir -p "/setup/IP2L_IPv4"
mkdir -p "/setup/IP2L_IPv6"

# List of folders to monitor

monitor_directory1() {
  while true; do
    filepath=$(inotifywait -q -e close_write "/setup/GreynoiseIPv4" | awk '{print $3}')
    echo "Change detected in /setup/GreynoiseIPv4"
    echo "File name: $filepath"
    python3 update_greynoise_ipv4.py "$filepath"
  done
}
monitor_directory1 &

# Monitor directory 2 in the background
monitor_directory2() {
  while true; do
    filename=$(inotifywait -q -e close_write "/setup/GreynoiseIPv6" | awk '{print $3}')
    echo "Change detected in /setup/GreynoiseIPv6"
    echo "File name: $filename"
    python3 update_greynoise_ipv6.py "$filepath"

  done
}
monitor_directory2 &

# Monitor directory 3 in the background
monitor_directory3() {
  while true; do
    filename=$(inotifywait -q -e close_write "/setup/IP2L_IPv4" | awk '{print $3}')
    echo "Change detected in /setup/IP2L_IPv4"
    echo "File name: $filename"
    if [ "$filename" = "IPv4_Elastic.csv" ]; then
      python3 update_ip2l_ipv4.py "$filename"
    else 
      echo "Invalid filename: $filename, should be IPv4_Elastic.csv. Ignore if it's updating."
    fi
  done
}
monitor_directory3 &

# Monitor directory 4 in the background
monitor_directory4() {
  while true; do
    filename=$(inotifywait -q -e close_write "/setup/IP2L_IPv6" | awk '{print $3}')
    echo "Change detected in /setup/IP2L_IPv6"
    echo "File name: $filename"
    if [ "$filename" = "IPv6_Elastic.csv" ]; then
      python3 update_ip2l_ipv6.py "$filename"
    else 
      echo "Invalid filename: $filename, should be IPv6_Elastic.csv. Ignore if it's updating."
    fi
  done
}
monitor_directory4 &

# Wait for all background processes to finish
wait


# mkdir -p "$folder1"
# mkdir -p "$folder2"
# mkdir -p "$folder3"
# mkdir -p "$folder4"
# while true; do
#   event=$(inotifywait -r -e close_write --format "%w%f" -q -m "$folder1" "$folder2" "$folder3" "$folder4")
#   folder=$(dirname "$event")
#   filename=$(basename "$event")

#   echo "Folder: $folder"
#   echo "Filename: $filename"

#   case $folder in
#     "$folder1")
#       python "$script1" "$folder1/$filename" &
#       ;;
#     "$folder2")
#       python "$script2" "$folder2/$filename" &
#       ;;
#     "$folder3")
#       if [ "$filename" = "IPv4_Elastic.csv" ]; then
#         python "$script3" "$folder3/$filename" &
#       else 
#         echo "Invalid filename: $filename, should be IPv4_Elastic.csv. Ignore if it's updating."
#       fi
#       ;;
#     "$folder4")
#       if [ "$filename" = "IPv6_Elastic.csv" ]; then
#         python "$script4" "$folder4/$filename" &
#       else 
#         echo "Invalid filename: $filename, should be IPv6_Elastic.csv. Ignore if it's updating."
#       fi
#       ;;
#     *)
#       echo "Unknown file: $filename, ignore if it's updating."
#       ;;
#   esac
# done


# Monitor for changes to IPv4.csv and IPv6.csv files
#/setup/IPv4_Elastic.csv /setup/IPv6_Elastic.csv /setup/Greynoise_IPv4.json /setup/Greynoise_IPv6.json
# Monitor for changes to files in /setup directory

# while true; do
#   filename=$(inotifywait -e close_write /setup | awk '{print $3}')
#   case $filename in
#     "IPv4_Elastic.csv")
#       python update_ip2l_ipv4.py &
#       ;;
#     "IPv6_Elastic.csv")
#       python update_ip2l_ipv6.py &
#       ;;
#     "Greynoise_IPv4.json")
#       python update_greynoise_ipv4.py &
#       ;;
#     "Greynoise_IPv6.json")
#       python update_greynoise_ipv6.py &
#       ;;
#     *)
#       echo "Unknown file: $filename , ignore if it's updating."
#       ;;
#   esac
# done