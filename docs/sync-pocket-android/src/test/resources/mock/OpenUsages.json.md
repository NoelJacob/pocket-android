# sync-pocket-android/src/test/resources/mock/OpenUsages.json

## What this is

A checked-in mock API payload (75 lines) used as fixture input: parsed by ThingMock into example Things so tests exercise realistic shapes without network.

## How it fits

Loaded by ThingMock streams and parsed with NO_ALIASES config; parser, compression, and equality tests assert against the resulting Things. Editing this re-baselines every test that loads it.

## Entries

- Top-level keys: `_type`, `variety`, `variety_list`, `variety_map`, `interface`, `interface_list`, `interface_map`
- Shape: object fixture, 75 lines
