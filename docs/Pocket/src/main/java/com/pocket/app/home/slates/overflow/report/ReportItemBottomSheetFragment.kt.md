# Pocket/src/main/java/com/pocket/app/home/slates/overflow/report/ReportItemBottomSheetFragment.kt
## What this is
This is the "Report this item" bottom sheet: a small form where the user picks why a recommended story is bad (broken link, wrong category, explicit, offensive, misinformation, other + free text) and submits. On submit it shows a confirmation snackbar and closes.
## How it fits
Opened from `RecommendationOverflowBottomSheetFragment` via `newInstance(url, corpusRecommendationId)`. The layout is databound to `ReportItemBottomSheetViewModel` (reason radio rows + submit button enablement flow from its `uiState`). This file adds three glue pieces: `setupTextWatcher` forwards the "other" free-text field into the ViewModel, `setupEditText` fixes nested scrolling so the text field scrolls inside the sheet instead of dragging it, and `setupEventObserver` shows the `PktSnackbar` confirmation on `ShowToastAndClose` and hides the keyboard on `HideKeyboard`.
## Key pieces
- `setupEditText()` — `requestDisallowInterceptTouchEvent(true)` on touch lets the `EditText` receive scroll gestures that the bottom sheet would otherwise steal; returns true to consume the touch.
- `setupTextWatcher()` — `doOnTextChanged` (a core Android KTX extension) pushes every keystroke to `onOtherTextChanged`.
- `setupEventObserver()` — builds the confirmation snackbar (`PktSnackbar.Type.DEFAULT_DISMISSABLE` with title + message from resources) and dismisses; `HideKeyboard` delegates to the `hideKeyboard()` util.
## Junior notes
- The `newInstance` fields (`url`, `corpusRecommendationId`) don't survive process death — a rotation is fine (ViewModel survives), but a killed process loses them; consistent with the other sheets here.
- The touch-listener `SuppressLint("ClickableViewAccessibility")` is intentional: the listener only fixes gesture routing, and accessibility actions still go through the `EditText` itself.

