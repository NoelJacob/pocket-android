# Pocket/src/main/java/com/pocket/app/list/add/AddUrlBottomSheetViewModel.kt
## What this is
This is the logic behind the Add-URL sheet: it holds the text-field content, validates it as a URL on Save, saves it via the shared `Save` use case, and tells the sheet to close on success. UI state uses Compose `mutableStateOf` (observable holders that re-render Compose UI on change) instead of `StateFlow`, since the UI is Compose.
## How it fits
Hilt provides it (`@HiltViewModel` + `@Inject`: the `Save` use case is supplied automatically). The Compose UI reads `textFieldValue`/`textFieldIsError` directly and calls `onTextFieldValueChange`/`onSaveButtonClick`. Save runs in `viewModelScope` (a coroutine scope tied to the ViewModel; coroutines = background tasks): `UrlFinder.getFirstUrlOrNull` extracts the first URL from pasted text (so surrounding prose is fine), `Save.Result.Success` emits `NavigationEvent.Close` (collected by the fragment to dismiss), `NotLoggedIn` is a no-op because the sheet is only reachable when signed in. Anything without a URL sets `textFieldIsError = true`, which paints the field red; typing again clears it.
## Key pieces
- `onSaveButtonClick()` — validate-then-save-then-close; the whole behavior in ~15 lines.
- `onTextFieldValueChange()` — sets text and clears the error in one step so the red state never sticks after the user fixes input.
- `NavigationEvent.Close` — the only event; one-shot `SharedFlow` (fire-and-forget with no replay).
- `onViewShown()` — empty analytics hook (mirrors other bottom-sheet VMs); safe to wire tracking into later.
## Junior notes
- `var ... by mutableStateOf` with `private set` means only the ViewModel mutates while Compose reads freely — don't expose the setter or two writers can fight.
- `UrlFinder` accepts any text containing a URL, not just bare URLs — validation failures mean genuinely no link present, so the error copy should say that.

