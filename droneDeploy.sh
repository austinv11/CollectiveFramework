#!/bin/bash
CF_VERSION=`cat VERSION.txt`
FILENAME="CollectiveFramework-$CF_VERSION-deobf.jar"
LOCAL_FILENAME="build/libs/$FILENAME"
curl -T "$LOCAL_FILENAME" -uaustinv11:"$API_KEY" "https://api.bintray.com/content/austinv11/maven/CollectiveFramework/$CF_VERSION/$FILENAME"
curl -X POST -uaustinv11:"$API_KEY" "https://api.bintray.com/content/austinv11/maven/CollectiveFramework/$CF_VERSION/publish"
