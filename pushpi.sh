#!/bin/sh
# Push your local repo contents to the raspberry pi server for testing
#
# Note:
# - requires pub key
# - does not copy dot files (won't copy .git)
scp -r * csci3308@zachlef.in:~/project/
