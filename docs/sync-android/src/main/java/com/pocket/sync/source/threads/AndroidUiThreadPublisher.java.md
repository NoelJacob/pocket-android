# sync-android/src/main/java/com/pocket/sync/source/threads/AndroidUiThreadPublisher.java

## What this is

A Publisher that runs every callback on Android's main (UI) thread: the single publish method hops whatever thread finished the work over to the thread allowed to touch views. Without it, subscriber updates and async completions would arrive on pool threads and crash on first view access. It is tiny because all policy lives in the caller; this only chooses the thread.

## How it fits

Wired as the app's default Publisher into AppSource and PublishingSubscriber chains, so screen callbacks always land safely. Tests use Publisher.CALLING_THREAD instead to stay deterministic and synchronous.

## Key pieces

- `publish` — the main-thread hop for one callback delivery

## Junior notes

- Main-thread delivery serializes UI work: keep subscriber bodies small or a slow update stalls every frame behind it.
