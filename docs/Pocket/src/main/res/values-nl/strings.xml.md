# Pocket/src/main/res/values-nl/strings.xml

## What this is

This is the Dutch translation of the app's string table: the same keys as `values/strings.xml`, with translated values (464 entries). Translators work through Smartling (see the `smartling.*` comments in the base file); translators never add or remove keys here.

## How it fits

Android picks this file automatically when the device locale matches: resource resolution falls back to `values/strings.xml` for any key missing here, so a partial translation still builds and runs. Referenced the same way from code (`getString(R.string.*)`) and layouts (`@string/*`) regardless of locale.
