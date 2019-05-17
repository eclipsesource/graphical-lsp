#!/bin/bash
diff=$(git diff --name-only HEAD^| grep "^client" | grep -v "^client/yarn.lock")
if [ -z "$diff" ]
then
    echo "No NPM packages have been changed. Skip deployment"
else
    echo "Deploy next-packages to npm"
    cd ../client
    yarn run publish:next
fi
