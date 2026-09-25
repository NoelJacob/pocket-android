# Pocket/src/main/java/com/pocket/app/list/list/empty/EmptyListView.kt

## What this is
The illustrated empty-state panel shown when a list tab has nothing to display. There is one variant per situation: signed-out, all-saves empty, favorites, tagged, specific tag, highlights, archive, and search-no-results.

## How it fits
Embedded in the My List screen layout; `MyListViewModel`'s screen state decides which variant is visible via the `set*Visible` setters. Each variant's help button opens a Pocket support article in a Custom Tab (an in-app browser overlay), and the signed-out variant gets its button wired by the host screen.

## Key pieces
- Per-variant help buttons in `init` — WHY: each empty state links to the matching help article (saving, tagging, archive) so a confused user gets an explanation, not a dead screen.
- `setOnSignedOutButtonClick` — WHY: the only action the view does not own; the host passes "log in" behavior in.
- `setAllEmptyVisible`, `setFavoriteEmptyVisible`, `setTaggedEmptyVisible`, `setSpecificTagEmptyVisible`, `setHighlightedEmptyVisible`, `setArchiveEmptyVisible`, `setSearchEmptyVisible`, `setSignedOutEmptyVisible` — WHY: one toggle per variant; the ViewModel shows exactly one at a time.

## Junior notes
- Links use `CustomTabsIntent` (Chrome Custom Tab), which keeps the user in-app with shared login — do not replace with a plain browser `ACTION_VIEW` intent.
- This view only toggles visibility; the decision of *which* empty state applies lives in the ViewModel, so look there if the wrong art shows.
