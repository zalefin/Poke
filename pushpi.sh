#!/bin/sh
# Push your local repo contents to the raspberry pi server for testing
#
# Note:
# - requires pub key
# - does not copy dot files (won't copy .git)

POKEPORT=3070
# Remove all existing in remote
ssh -p $POKEPORT csci3308@zachlef.in "rm -rf ~/project/*"
# Create tarball
tar czf transfer.tar.gz *
# Copy tarball to remote
scp -P $POKEPORT transfer.tar.gz csci3308@zachlef.in:~/project/
# Extract and remove
ssh -p $POKEPORT csci3308@zachlef.in "cd ~/project && tar xzf transfer.tar.gz && rm transfer.tar.gz"
# Remove local
rm transfer.tar.gz
