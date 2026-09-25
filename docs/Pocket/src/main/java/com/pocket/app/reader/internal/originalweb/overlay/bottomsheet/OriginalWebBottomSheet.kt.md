# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/overlay/bottomsheet/OriginalWebBottomSheet.kt
## What this is
The contract for the reader menu shown over the original web page (main save/archive action, listen, share, switch-to-article, favorite, tags, mark-as-viewed, delete). It declares the button taps the sheet supports and every one-shot outcome, keeping the overlay fragment and its ViewModel in agreement.
## How it fits
`OriginalWebBottomSheetFragment` collects `Event` and performs navigation (sign-in, share dialog, tag screen, listen playback, back to the app); the ViewModel (sibling `OriginalWebBottomSheetViewModel`) implements `Initializer` + `ButtonInteractions` and emits the events. The layout's buttons bind to the interactions.
## Key pieces
- `Initializer.onInitialized(url)` — supplies the page URL the sheet acts on.
- `ButtonInteractions` — one method per control: main save/archive action, listen, share, switch-to-article, favorite, add-tags, mark-as-viewed, delete.
- `Event` — `GoBack`, `GoToSignIn`, `OpenListen(track)` (track = the text-to-speech audio for the article), `SwitchToArticleView`, `OpenTagScreen(item)`, `ShowShare(title)`, and the saved/archived/re-added toasts.
## Junior notes
- The toast trio mirrors the collection screen's pattern: the ViewModel chooses the wording (saved vs archived vs re-added), the fragment just shows it.
- `OpenListen` carries a `Track` rather than a URL because playback needs the resolved listening stream, not just the page address.
