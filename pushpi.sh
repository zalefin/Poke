#!/bin/sh
# Push your local repo contents to the raspberry pi server for testing
#
# Note:
# - requires pub key
# - does not copy dot files (won't copy .git)

# Remove all existing in remote
ssh csci3308@zachlef.in "rm -rf ~/project/*"
# Copy local to remote
scp -r * csci3308@zachlef.in:~/project/
