# Pocket/src/main/java/com/pocket/app/reader/internal/initial/InitialFragment.kt
## What this is
The reader's blank starting page. It shows nothing meaningful itself — it exists so the reader's navigation graph always has a valid first destination before the app decides whether the URL is an article, a collection, or an original-web page.
## How it fits
Lives inside `ReaderFragment`'s nested nav graph as the start destination. It never fetches data (`InitialViewModel` is empty); it only implements `Reader.NavigationEventHandler`, translating the reader's routing decision (`GoToArticle` / `GoToCollection` / `GoToOriginalWeb`) into a `switchTo*` navigation call that replaces this placeholder with the real screen.
## Key pieces
- `handleNavigationEvent(event)` — the whole behavior: each `Reader.NavigationEvent` maps to the matching `InitialFragmentDirections.switchTo*` call via `navigateSafely` (a helper that ignores duplicate/rapid navigations).
- `navController` — resolved from the parent `NavHostFragment`, since this fragment is nested and has no direct controller.
- `onCreate` sets `showsDialog = false`, marking this a full-screen destination rather than a dialog.
## Junior notes
- `switchTo*` (replace) vs `enter*` (push) matters: from this placeholder there is nothing to go back to, so it always replaces itself — siblings like `CollectionFragment` push when the user drills deeper.
- If you see a flash of this screen on open, that's expected: it's the one frame between reader launch and the routing event arriving.
