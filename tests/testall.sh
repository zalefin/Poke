#!/usr/bin/env bash
for f in $(ls test_*)
do
    echo "Test $f"
    python3 $f
done
