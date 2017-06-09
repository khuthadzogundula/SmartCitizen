#!/bin/bash

if [ $TRAVIS_PULL_REQUEST = "false" ]; then
  ${ANDROID_HOME}tools/bin/sdkmanager --channel=3 \
    "tools" "platform-tools" "build-tools;${BUILD_TOOLS_VERSION}" "platforms;android-O" \
    "extras;google;m2repository"
fi