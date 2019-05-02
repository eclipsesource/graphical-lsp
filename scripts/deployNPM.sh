#!/bin/bash
if 	git diff --name-only HEAD^| grep -q "^client" | grep -v "^client/yarn.lock"
then
    echo "Deploy next-packages to npm"
    cd ../client
    yarn run publish:next
else
    echo "No NPM packages have been changed. Skip deployment"
fi