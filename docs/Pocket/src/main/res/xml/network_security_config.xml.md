# Pocket/src/main/res/xml/network_security_config.xml

## What this is

App configuration: Network security policy: cleartext allowed by default (local dev server), pinned-off for Pocket production domains.

## How it fits

Wired in from `AndroidManifest.xml` or framework calls (`res/xml/` = machine-readable config, not layouts); changing it changes app behavior without touching code.
