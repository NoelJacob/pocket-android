# Pocket/src/main/java/com/pocket/app/list/add/AddUrlBottomSheetFragment.kt
## What this is
This is the "Add URL" bottom sheet: a text field plus Save button (written in Compose, Android's declarative UI toolkit) where the user pastes a link to save it to Pocket. The fragment itself is a thin shell — the UI is three `@Composable` functions driven by `AddUrlBottomSheetViewModel`.
## How it fits
Opened from `MyListFragment` (`ShowAddUrlBottomSheet` event) and from the add-choice popup (`onAddUrlClicked`). The fragment renders `content { PocketTheme { AddUrlBottomSheet() } }` (a Fragment-Compose bridge that makes the sheet's view a Compose hierarchy) and collects `navigationEvents` while RESUMED, dismissing on `Close`. The stateful `AddUrlBottomSheet(viewModel)` overload reads `textFieldValue`/`textFieldIsError` from the ViewModel and forwards keystrokes and button taps; the stateless overload lays out the rounded-top `Surface`, title, `AddUrlTextField`, and full-width Save `BoxButton`, with error styling when the text isn't a valid URL.
## Key pieces
- `AddUrlBottomSheetFragment.newInstance()` — trivial factory; shows via `childFragmentManager`.
- `onViewCreated` collector — `repeatOnResumed { navigationEvents.collect ... }` dismisses the sheet after a successful save.
- `AddUrlBottomSheet(...)` (two overloads) — the standard Compose split: stateful wiring vs stateless layout, so the layout is previewable with fake values.
- `AddUrlTextField` — single-line URL field with error flag; `AddUrlBottomSheetPreview` / `AddUrlBottomSheetErrorPreview` render both states in the IDE.
## Junior notes
- `androidx.fragment.compose.content` replaces `onCreateView`'s XML inflation — don't call `inflate` here; the Compose content *is* the view.
- `viewModel()` inside the `@Composable` (lifecycle-viewmodel-compose) scopes to the fragment automatically — passing a different ViewModel instance would silently break dismissal.

