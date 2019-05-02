#!/bin/bash
if 	git diff --name-only HEAD^| grep -q "^server"
then
    echo "Deploy maven snapshot plugins"
    cd ../server
    mvn deploy --settings ./settings.xml -Dmaven.test.skip=true -U -Prelease
else
    echo "No maven packages have been changed. Skip deployment"
fi