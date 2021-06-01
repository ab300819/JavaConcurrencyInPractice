#!/usr/bin/env zsh

javac -g -verbose -d bin/ "$1"  -cp "$JAVA_HOME"/lib/dt.jar

