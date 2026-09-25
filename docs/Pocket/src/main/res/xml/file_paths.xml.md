# Pocket/src/main/res/xml/file_paths.xml

## What this is

App configuration: FileProvider path map: exposes tmp_avatar/ and evernote_files/ for sharing via content URIs.

## How it fits

Wired in from `AndroidManifest.xml` or framework calls (`res/xml/` = machine-readable config, not layouts); changing it changes app behavior without touching code.
