# Pocket/src/main/java/com/pocket/app/add/AddActivity.java
## What this is
The transparent share-target screen: when the user taps "Add to Pocket" in another app's share sheet (ACTION_SEND) or opens a `getpocket.com/save` link (ACTION_VIEW), this activity extracts the URL, saves it via the sync engine, and shows a confirmation, all without a visible app window. With the quick-save overlay enabled it shows the Pocket confirmation card (save + tag shortcut); otherwise it shows a stock toast and exits.
## How it fits
The system (or `PocketUrlHandlerActivity` for save links) starts it with the shared intent. `IntentItemUtil.from` parses URL/title, `AddItemFromIntentUtil.add` performs the save with `Interaction.on(this)` attribution, and `onSaved` renders the result. Its buttons deep-link onward: "saved" opens Pocket (`DeepLinks.newPocketIntent`), "tag" opens `ItemsTaggingActivity/Fragment`. Logged-out users get a login nudge that routes to the default activity.
## Key pieces
- `onCreate`: rejects non-save ACTION_VIEW URLs to the browser, sizes itself fullscreen-transparent, finishes on outside touch, branches logged-out toast vs `commitSave`.
- `commitSave`: shows `AddOverlayView` immediately (before the save completes) when `SaveExtension.isOn`, then fires the async save; WHY show-first is perceived speed: the card is already animating while the network runs.
- `onSaved`: invalid URL hides the overlay with an error; duplicate vs fresh only differ in toast text when the overlay is off, and in tag-button wiring when on.
- `startTimeout`/`cancelTimeout` (6.5s): auto-finish so a stuck save never strands an invisible activity; `onStop` also finishes if the user navigates away.
- `startPocketActivity` / `startTagActivity` / `getVisiblePocketMultiWindow`: multi-window awareness; if a Pocket task is already visible they launch inside it, else as fresh overlay tasks with clear-top/new-task flags.
- `getActionContext`: tags saves with the save-extension view and overlay on/off UI variant for analytics.
## Junior notes
- Exported activity means hostile input: it only trusts `IntentItemUtil`'s defensive parse, and `isUserPresent()` returns false so share-saves never emit app-open analytics.
- It `finish()`es on stop and on timeout: never start long-lived work here or hold its context; the save itself lives in the sync engine, not the activity.
