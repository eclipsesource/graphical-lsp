#!/bin/bash

cd ../client/packages



function setVersion() {
	if [[ ! "$1" =~ 'node_modules/' ]]
	then 
		echo "Setting '$3' package version to  $2 in $1"
		mv $1 $1.orig
		sed -e 's/\("'$3'": \)\"[a-z0-9.]*\"/\1"'$2'"/' $1.orig > $1 
		
		rm -f $1.orig
	fi
} 

if [ -z "$1" ] || ([ "$1" != "next" ] && [ "$1" != "latest" ])
then
	echo Sets the version of the glsp dependencies in the package.json files.
	echo 
	echo Usage:
	echo sh theia-version.sh '<next|latest>'
else
	for file in `find * -name package.json -print`
	do
	setVersion $file $1 "glsp-sprotty"
	setVersion $file $1 "glsp-theia-extension"
	setVersion $file $1 "theia-glsp"
	done
fi
