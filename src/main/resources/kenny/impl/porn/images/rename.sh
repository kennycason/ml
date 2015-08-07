#!/bin/sh

sequence=0

find . -print0 | while read -d $'\0' file;
do
    renamedFile="i$sequence.png"
    currentFile="$(echo $file)"
    echo "renaming $currentFile to $renamedFile"
    mv "$currentFile" "$renamedFile"
    sequence=$(($sequence+1))
done

exit 0