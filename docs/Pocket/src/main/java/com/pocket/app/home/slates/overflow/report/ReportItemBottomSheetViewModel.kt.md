# Pocket/src/main/java/com/pocket/app/home/slates/overflow/report/ReportItemBottomSheetViewModel.kt
## What this is
This is the ViewModel for the report-item form. It tracks which report reason is selected (driving which radio row highlights, whether the free-text box shows, and whether Submit is enabled) and emits close/keyboard events on submit. Notably, submit currently only shows the confirmation UI — it does not send the report anywhere.
## How it fits
The fragment calls `onInitialized(url, corpusRecommendationId)` to stash context, databinds `uiState` (a `StateFlow`, i.e. observable state) to the reason rows and submit button, and observes `events` (a `SharedFlow` of one-shot happenings) for `ShowToastAndClose` / `HideKeyboard`. Each reason tap sets `submitButtonEnabled = true` and the matching `ReportReason`; every reason except Other also hides the keyboard (the text box is going away).
## Key pieces
- `UiState(submitButtonEnabled, reportReason)` — starts as `None` (nothing selected, submit disabled).
- `ReportReason` sealed class — each subtype flips exactly one `...Selected` boolean databound to its row's checked state; `Other` additionally sets `otherTextBoxVisible = true`.
- `onOtherTextChanged(text)` — stores free text in a plain field (not state — typing doesn't need to re-render anything).
- `onSubmitClicked()` — emits `ShowToastAndClose`; the stashed `url`/`corpusRecommendationId`/`otherText` are currently unused, so wiring a real reporting API means starting here.
- `ReportItemInteractions` — lists all eight XML-callable actions.
## Junior notes
- Single-selection is enforced structurally (one `reportReason` object, not six booleans), so adding a new reason means adding one subclass + one `onXClicked` — follow that pattern.
- `_uiState.edit { copy(...) }` is a concise update helper (read-modify-write on the `MutableStateFlow`); concurrent edits could theoretically clobber, but all calls here come from the main thread.

