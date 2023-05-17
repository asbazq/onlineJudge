#!/bin/bash

filename=${1%.*}
extension=${1##*.}
language=$2
params="$3"

# check if the file exists
if [ ! -f "$1" ]; then
        echo "File not fond: $1"
        exit 1
fi

# parse the String
IFS=',' read -ra args <<< "$params"


# Check program language
case $language in

	c)
		#compile and execute the c file
                gcc -o "$filename" "$1"
                su -c "./$filename ${args[@]}" dummy
                exit
        ;;
        java)
                #compile and execute the java file
                javac "$1"
                su -c "java $filename ${args[@]}" dummy
                exit
        ;;
        python)
                #compile and execute the python file
		  su -c "python3 $1 ${args[@]}" dummy
                exit
        ;;
                *)
                echo "Invalid language: $language"
                exit 1
        ;;
esac



                                                            
