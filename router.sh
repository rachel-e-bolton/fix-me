#!/bin/bash

JVM="/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/bin/java"

ROUTER_CLASS=$(cd .. && find ~+ -type f -name Router.class)
echo ${ROUTER_CLASS}

ROUTER_DIRECTORY=$(dirname ${ROUTER_CLASS})
echo ${ROUTER_DIRECTORY}

${JVM} -Dfile.encoding=UTF-8 -cp ${ROUTER_DIRECTORY} fix.router.Router