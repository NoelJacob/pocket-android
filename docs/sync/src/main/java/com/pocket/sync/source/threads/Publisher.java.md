# sync/src/main/java/com/pocket/sync/source/threads/Publisher.java

## What this is

Declares where async callbacks run: a Publisher decides the thread (or custom dispatch) on which PendingResult listeners and Subscriber updates are invoked. Sources take one at construction so threading policy stays with the app, not buried in engine internals. The CALLING_THREAD constant (used widely in tests) invokes callbacks inline on whatever thread finished the work.

## How it fits

AppSource routes all listener and subscriber delivery through its Publisher; Android wires AndroidUiThreadPublisher so UI callbacks land on the main thread. ThreadPublisher is the dedicated-thread implementation.

## Key pieces

- `publish contract` — the single dispatch decision point for all async callback delivery
- `CALLING_THREAD` — the inline-test publisher that skips thread hops for deterministic unit tests

## Junior notes

- CALLING_THREAD in production UI code would run network completions on pool threads; always inject the UI publisher in the app.
