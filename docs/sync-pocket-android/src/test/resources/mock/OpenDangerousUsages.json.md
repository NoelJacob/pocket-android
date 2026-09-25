# sync-pocket-android/src/test/resources/mock/OpenDangerousUsages.json

## What this is

A checked-in mock API payload (61 lines) used as fixture input: parsed by ThingMock into example Things so tests exercise realistic shapes without network.

## How it fits

Loaded by ThingMock streams and parsed with NO_ALIASES config; parser, compression, and equality tests assert against the resulting Things. Editing this re-baselines every test that loads it.

## Entries

- Top-level keys: `_type`, `dangerous_interface`, `dangerous_interface_list`, `dangerous_impl`, `dangerous_impl_list`
- Shape: object fixture, 61 lines
