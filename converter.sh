#!/bin/bash

file="$1"
language=$2
params="$3"

# Check if the file exists
if [ ! -f "$1" ]; then
        echo "File not found: $1"
        exit 1
fi

# Get the file's extension
ext="${file##*.}"

# Parse the string
IFS=',' read -ra args <<< "$params"

# Get current working directory
cwd=$(pwd)

dir=$(dirname "$1")
filename=$(basename "$1" .$ext)

# Check program language
case $language in
        c)
                # Compile and execute the C file
                gcc -o "$filename" "$1"
                if [ $? -ne 0 ]; then
                        echo "Compilation failed"
                        exit 1
                fi

                # Run the C program in dummy with the given parameter file
                cd "$dir"
                sudo -u dummy "./$filename" "${args[@]}"
                cd "$cwd"
        ;;
        java)
                # Compile and execute the Java file
                javac "$file"
                if [ $? -ne 0 ]; then
                        echo "Compilation failed"
                        exit 1
                fi

                # Run the Java program in dummy with the given parameter file
                cd "$dir"
                sudo -u dummy "java" "$filename" "${args[@]}"
                cd "$cwd"
        ;;
        python)
                # Compile and execute the Python file in dummy
                su -c "python3 $1 ${args[@]}" dummy
        ;;
        *)
                echo "Invalid language: $language"
                exit 1
        ;;
esac
