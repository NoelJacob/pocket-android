# Pocket/src/main/res/values/donottranslate.xml

## What this is

This file holds strings that must never be translated: the app/brand name, Premium font display names, internal dev-settings labels, and Google client ids/keys from `google-services.json`. `translatable="false"` keeps export pipelines from sending them to translators.

## How it fits

Referenced exactly like normal strings (`@string/<name>`); the font names feed the reader font picker, and the Google ids configure sign-in/push. Never put user-facing sentences here.
