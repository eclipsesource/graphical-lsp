#!/bin/bash
if 	git diff --name-only HEAD^| grep -q "^server"
then
    # Setup configuration variables
    ROOT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )"/.. && pwd )"
    REPO_DIR=$ROOT_DIR/.repo
    
    rm -rf $REPO_DIR
    mkdir $REPO_DIR
    # Clone glsp-p2 and switch to new skeleton branch
    cd $REPO_DIR || exit
    git clone  https://${GLSP_P2_USER}:${GLSP_P2_CI_ACCESS}@github.com/eclipsesource/glsp-p2.git
    cd glsp-p2 || exit
    
    git checkout plugin_skeleton
    git checkout -b new_deploy
    
    # (Re)generate plugis
    cd scripts || exit
    . ./functions.sh
    . ./generate.sh
    
    GLSP_SNAPSHOT_VERSION=$(resolve_version com.eclipsesource.glsp glsp-parent 1.2.0-SNAPSHOT)
    
    cd ${REPO_DIR}/glsp-p2/targetplatforms || exit
    
    # Regenerate .target from tpd file
    mvn clean install

    cd .. 

    # Build and deploy composite repository
    mvn clean install -Pdeploy-composite

    git config user.name \""${GLSP_P2_USER}"\"
    git config user.mail \""${GLSP_P2_MAIL}"\"
    # Push changes to glsp-p2 repository
    git add plugins/
    git commit -m "Update to version $GLSP_SNAPSHOT_VERSION"
    git tag -a "v$GLSP_SNAPSHOT_VERSION" -m "$GLSP_SNAPSHOT_VERSION"
    git push -f origin new_deploy:master
    git push origin "v$GLSP_SNAPSHOT_VERSION"
else
    echo "No maven packages have been changed. Skip deployment"
fi