#!/usr/bin/env bash

# Script for running emulator using default `emulator` tool

set -ex

forward_loggers() {
  mkdir /tmp/android-unknown
  mkfifo /tmp/android-unknown/kernel.log
  mkfifo /tmp/android-unknown/logcat.log
  echo "emulator: It is safe to ignore the warnings from tail. The files will come into existence soon."
  tail --retry -f /tmp/android-unknown/goldfish_rtc_0 | sed -u 's/^/video: /g' &
  cat /tmp/android-unknown/kernel.log | sed -u 's/^/kernel: /g' &
  cat /tmp/android-unknown/logcat.log | sed -u 's/^/logcat: /g' &
}

if [[ -z "${VERSION}" ]]; then
    echo_error "You must specify VERSION environment variable"
    exit 1
fi

emulator_name="emulator_${VERSION}"
sd_card_name="/sdcard.img"

emulator_arguments=( -avd ${emulator_name} -sdcard ${sd_card_name} -verbose )

if [[ ${WINDOW} == "true" ]]; then
    binary_name="qemu-system-x86_64"

    if [[ -z "${DISPLAY}" ]]; then
        export DISPLAY=":0"
    fi

    if [[ ${GPU_ENABLED} == "true" ]]; then
        echo "Rendering: Window host mode is enabled on ${DISPLAY} display (make sure, that you have configured Nvidia on host and pass X11 socket)"
        emulator_arguments+=( -gpu host )
    else
        echo "Rendering: Window swiftshader (software) rendering mode is enabled on ${DISPLAY} display (make sure, that you pass X11 socket)"
        emulator_arguments+=( -gpu swiftshader_indirect )
    fi
else
    binary_name="qemu-system-x86_64-headless"

    echo "Rendering: Headless swiftshader (software) rendering mode is enabled"
    emulator_arguments+=( -no-window -gpu swiftshader_indirect )
fi

if [[ "${SNAPSHOT_DISABLED}" == "true" ]]; then
    echo "Snapshots: Emulator will be ran without snapshot feature"
    emulator_arguments+=( -no-snapshot )
else
    echo "Snapshots: Emulator will be ran with loading snapshot (for using emulator with snapshot on CI)"
    emulator_arguments+=( -snapshot ci -no-snapshot-save )
fi

forward_loggers

emulator_arguments+=( -no-boot-anim -no-audio -partition-size 2048 )
emulator_arguments+=( -logcat-output /tmp/android-unknown/logcat.log )

cd /opt/android-sdk/emulator
echo "Run ${binary_name} binary for emulator ${emulator_name} with abi: x86 (Version: ${VERSION})"
echo "no" | ./qemu/linux-x86_64/${binary_name} "${emulator_arguments[@]}"
