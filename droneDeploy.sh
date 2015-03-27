#!/bin/bash
KEY=API_KEY
VERSION=CF_VERSION
echo "$VERSION"
FILENAME="CollectiveFramework-$VERSION-deobf.jar"
LOCAL_FILENAME="build/libs/$FILENAME"
URL="https://api.bintray.com/content/austinv11/maven/CollectiveFramework/$VERSION/$FILENAME"
curl -T LOCAL_FILENAME -uaustinv11:"$KEY" URL
