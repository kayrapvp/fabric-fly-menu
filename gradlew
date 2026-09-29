#!/usr/bin/env sh
DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$DIR"
if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
else
    echo "Gradle is not installed or not on PATH."
    echo "Install Gradle and try again: https://gradle.org/install/"
    exit 1
fi
