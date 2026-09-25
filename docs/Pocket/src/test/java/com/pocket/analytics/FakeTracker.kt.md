# Pocket/src/test/java/com/pocket/analytics/FakeTracker.kt
## What this is
A test double for the `Tracker` analytics interface. It records every `track(event)` call in a public `events` list and no-ops all view-binding methods, plus an `assertTracked(event)` helper that asserts an event appears exactly once. Lets tests verify analytics without sending real data.
## How it fits
Injected wherever production code takes a `Tracker`: used by `MainViewModelTest`, `AddUrlBottomSheetViewModelTest`, and similar suites that assert `SavesEvents` / `ReaderEvents` were emitted. The fake exists because the real tracker needs Android views and a network pipeline; the no-op binding overrides satisfy the interface without them.
## Key pieces
- `FakeTracker(events)` — mutable event log; `track(event)` appends. WHY: observable record of what the code under test reported.
- `assertTracked(event)` — top-level helper asserting exactly one matching event. WHY: concise one-line analytics assertions.
- No-op `bindUiEntityType / bindContent / trackImpression / trackEngagement / trackContentOpen / trackAppOpen / trackVariantEnroll` overrides — required by the `Tracker` interface but irrelevant to unit tests, so they do nothing.
## Junior notes
- `Tracker` has many methods because production binds analytics to Android `View`s; the fake only cares about `track()`.
- `assertTracked` checks equality and count (exactly 1), so duplicate tracking fails the test — that is intentional.
