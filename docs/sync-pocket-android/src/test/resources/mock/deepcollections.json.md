# sync-pocket-android/src/test/resources/mock/deepcollections.json

## What this is

A checked-in mock API payload (4682 lines) used as fixture input: parsed by ThingMock into example Things so tests exercise realistic shapes without network.

## How it fits

Loaded by ThingMock streams and parsed with NO_ALIASES config; parser, compression, and equality tests assert against the resulting Things. Editing this re-baselines every test that loads it.

## Entries

- Top-level keys: `id`, `val0`, `obj0`, `ref_list0`, `ref_map0`, `val_list0`, `val_map0`, `obj_list0`, `obj_map0`
- Shape: object fixture, 4682 lines
