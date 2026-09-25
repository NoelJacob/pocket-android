# sync-pocket-android/src/test/resources/mock/getProfileFeed.json

## What this is

A checked-in mock API payload (3634 lines) used as fixture input: parsed by ThingMock into example Things so tests exercise realistic shapes without network.

## How it fits

Loaded by ThingMock streams and parsed with NO_ALIASES config; parser, compression, and equality tests assert against the resulting Things. Editing this re-baselines every test that loads it.

## Entries

- Top-level keys: `version`, `count`, `profile_key`, `feed`, `status`, `error`, `groups`
- Shape: object fixture, 3634 lines
