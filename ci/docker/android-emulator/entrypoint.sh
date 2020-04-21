#!/usr/bin/env bash

set -ex

./adb_redirect.sh
./run_desktop.sh &
./run_emulator.sh &
./run_tests.sh
