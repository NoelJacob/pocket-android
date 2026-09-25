# sync-pocket-android/src/test/java/com/pocket/sync/ThingMock.java

## What this is

Fixture factory for example Things with lots of state: thing()/string() builders plus streams() loading the mock JSON resources (feed, getNotifications, getProfileFeed, deepcollections, open usages) into parsed Things (Feed and friends) with NO_ALIASES config. Helpers for creating example Things is its stated job, and CompressionTest plus parser tests consume them heavily.

## How it fits

Centralizes fixture loading so tests share identical parsed state; ThingMock-backed tests fail together when a mock JSON changes.

## Key pieces

- `Things (feed/getNotifications/getProfileFeed/...)` — parsed heavy-state Things, one accessor per mock resource
- `Strings/streams` — raw-string and stream access to the underlying mock payloads
- `NO_ALIASES config` — neutral parsing so fixtures test shapes, not alias mappings

## Junior notes

- Fixtures are shared truth: editing a mock JSON re-baselines every test that loads it, so update expectations deliberately.
