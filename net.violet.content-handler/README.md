# Content Handler

## Description

Seem to be used to convert media file formats, such as WAV to MP3, etc. Uses wrapper shell scripts around tools like `ffmpeg` and `sox`.

## Pre-requisites

* `ffmpeg` and `sox` must be on the path.
* `ffmpeg` must support MP3 encoding (i.e. you probably need a specific package such as `libavcodec-extra-52` on Ubuntu)

## TODO

* Properly fix the unit tests so that they aren't filesize-based. Ideally open the resulting file with a Java MP3 library and check the metadata ?
* Replace the wrapper script by native Java libraries for the conversion ?

## Changes

* The unit tests were expecting precise sizes for converted files, which is a problem since it'll vary depending on the `ffmpeg` installation and other factors.
* This has been changed to expect a range so that they pass, but that's not a very good test either.