# Pocket/src/main/java/com/pocket/app/reader/toolbar/OverflowBuilder.kt
## What this is
This is a helper that builds the reader's three-dot overflow popup menu. Given a ToolbarOverflowUiState (which rows are allowed for this article) it creates only those MenuItem rows — display settings, view original, refresh, find in page, favorite, tags, highlights, mark as not viewed, delete, report — and shows them anchored to the overflow button. It contains no business logic; each tap just forwards to a ToolbarOverflowInteractions callback.
## How it fits
Called from ReaderToolbarView when it receives a ShowOverflow event carrying the overflow state. The callbacks it wires up land in ReaderToolbarDelegate (or a subclass ViewModel), which performs the actual favorite/delete/report work.
## Key pieces
- `showOverflow(anchorView, overflowUiState, toolbarInteractions)`: WHY the menu is data-driven — each `if (…Visible)` gate adds one MenuItem, so Article view and Original-web view show different rows without separate menu code.
## Junior notes
- ThemedPopupMenu is Pocket's styled popup; the anchorView both provides the context/theme and positions the popup — pass the overflow button itself, not an arbitrary view.
- Visibility flags come from the ViewModel, not this file — to add a menu row you add a flag to ToolbarOverflowUiState, a gate here, and a handler in the delegate.
