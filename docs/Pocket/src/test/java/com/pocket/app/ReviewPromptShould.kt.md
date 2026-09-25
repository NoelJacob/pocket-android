# Pocket/src/test/java/com/pocket/app/ReviewPromptShould.kt
## What this is
Behavioral tests for `ReviewPrompt`, the "please rate the app" prompt logic. It proves the prompt stays hidden by default and only shows when all gates pass: reader used enough, item scrolled far enough, prompt closed recently in the reader, not shown recently, and enough months since the last prompt; plus a long suppression after an actual review.
## How it fits
Guards production `com.pocket.app.ReviewPrompt`, which the reader screen consults via `shouldShow()`. The test builds real `Prefs(MemoryPrefStore())` and `FeatureStats` (usage counters) with a file-local `MutableClock` (a manually-advanced clock at the bottom of this file) instead of the system clock, so time gates are deterministic.
## Key pieces
- `setUp()` — wires real prefs/stats with `AppMode.PRODUCTION` and a no-op `Analytics`; WHY: tests the real gating logic, not mocks.
- `trackUse(times)` / `prepareSuccessfulConditions()` / `mockItemWithPosition(percent)` helpers — stage reader-use counts and fake article scroll positions; WHY: each gate gets an isolated scenario.
- `MutableClock` (bottom of file) — `Clock` subclass with settable time; WHY: simulates months passing without waiting.
- Scenario tests (`not show by default`, `show when criteria met`, `not show when ...`, `show only after more than 3 months...`, `should not show again ... after review`) — each flips one gate; WHY: pins the exact show/hide contract.
## Junior notes
- `FeatureStats` counts reader sessions in prefs; tests must call `trackUse` enough times before `shouldShow()` can return true.
- `AppMode.PRODUCTION` matters: review prompts are typically disabled in debug/beta modes, so the mode is fixed here.
