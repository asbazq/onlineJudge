#!/bin/sh

filename=${1%.*}
extension=${1##*.}
language=$2
param=$3

case $language in
	c)
		gcc -o $filename $1
		su -c "./$filename $param" dummy
		rm $filename
	;;
	java)
		javac $1
		su -c "java $filename $param" dummy
		rm "${filename}.class"
	;;
	python)
		su -c "python3 $1 $param" dummy
	;;
esac

