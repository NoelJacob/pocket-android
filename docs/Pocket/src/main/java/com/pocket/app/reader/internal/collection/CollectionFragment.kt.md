# Pocket/src/main/java/com/pocket/app/reader/internal/collection/CollectionFragment.kt
## What this is
The full-screen Collection view in the reader: title/author/intro header plus a scrolling list of member stories, each with save and overflow actions. This is what the user sees after tapping a Pocket Collection (a curated set of stories around a topic).
## How it fits
Hosted inside `ReaderFragment`'s nav graph; opened with a `url` nav argument (`CollectionFragmentArgs`). It creates `CollectionViewModel`, binds the layout to it via databinding (XML layouts bound to ViewModel fields), sets up the toolbar, story list, and event listener, then calls `viewModel.onInitialized(args.url)`. Story taps route through `readerFragment.openUrl(...)` so the reader pager can swipe between them; reader-level navigation events (`GoToArticle`, `GoToCollection`, `GoToOriginalWeb`) are handled here with `enter*` (push) vs `switchTo*` (replace) variants depending on `addToBackstack`.
## Key pieces
- `setupRecyclerView()` — installs `CollectionStoryAdapter` with a `MarkdownFormatter` (renders story excerpts' markdown, with link taps opening URLs in the reader), an `InstantChangeItemAnimator`, and a 2-column grid with `CollectionGridSpacingDecorator` on tablets vs a linear list with `CollectionSpacingDecorator` on phones.
- `setupToolbar()` — delegates the reader toolbar (save/archive/share/overflow, text-to-speech via `Listen`) to `viewModel.toolbar`.
- `setupUiStateListener()` — renders the intro markdown into `binding.intro` with clickable links.
- `setupEventListener()` — maps `CollectionScreen.Event` to UI: overflow bottom sheet, `openUrl` into the reader, saved/archived/re-added toasts, and sign-in via `AuthenticationActivity`.
- `handleNavigationEvent(...)` — reader pager callbacks that navigate to the next/previous item without losing back-stack semantics.
## Junior notes
- `navController` comes from the *parent* `NavHostFragment`, and `readerFragment` from the grandparent — this fragment is nested two levels deep, so `findNavController()` is only used in the original-web sibling; here navigation goes through those indirect references.
- `showsDialog = false` in `onCreate` marks this as a full-screen destination even though the base class can act as a dialog.
