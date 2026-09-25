# Pocket/src/main/java/com/pocket/app/reader/toolbar/ReaderToolbar.kt
## What this is
This is the contract object for the reader toolbar: it defines the events the toolbar can emit, the tap callbacks the toolbar view forwards, and the UI-state models the toolbar renders. It holds no logic — ViewModels implement the interaction interfaces and expose the state, while ReaderToolbarView binds them to the layout. Think of it as the shared vocabulary between the toolbar UI and whatever screen hosts it.
## How it fits
ReaderToolbarDelegate implements ToolbarInteractions, ToolbarOverflowInteractions, and ToolbarUiStateHolder; ReaderToolbarView collects ToolbarEvent emissions and performs navigation (back, sign-in, Listen playback, share sheet, overflow popup, tag screen, toast). Both the Article reader and the Original-web reader reuse this contract with different state values.
## Key pieces
- `ToolbarEvent`: one-shot outcomes (GoBack, GoToSignIn, OpenListen, Share, ShowOverflow, ShowTagScreen, ShowArticleReportedToast) — WHY navigation lives in the view layer, not the delegate.
- `ToolbarInteractions` / `ToolbarOverflowInteractions`: tap callbacks for the main bar (up/save/archive/re-add/listen/share/overflow) and for each overflow row.
- `ToolbarUiStateHolder.toolbarUiState`: StateFlow (observable state stream) the databound layout reads — databinding means XML layout attributes bound directly to these fields update automatically.
- `ToolbarUiState` / `ActionButtonState` (None/Save/Archive/ReAdd): exactly one primary action is visible at a time, driven by whether the item is unsaved, saved, or archived.
- `ToolbarOverflowUiState`: per-row visibility flags so Article vs web view show different overflow rows.
## Junior notes
- Sealed classes (ToolbarEvent, ActionButtonState) are closed sets the `when` in ReaderToolbarView must handle exhaustively — adding a variant forces you to handle it in the view.
- StateFlow (persistent state) vs SharedFlow events (one-shot navigation): visibility booleans live in state; "go back now" is an event.
