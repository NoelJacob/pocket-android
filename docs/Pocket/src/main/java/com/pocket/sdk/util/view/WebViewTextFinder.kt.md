# Pocket/src/main/java/com/pocket/sdk/util/view/WebViewTextFinder.kt
## What this is
A find-in-page controller that wires a `TextFinderLayout` search bar (input, match count, back/forward, close) to an Android `WebView` (the embedded browser view that renders articles). Typing searches the page asynchronously, the count label shows "match N of M", and arrows step through matches. Opening focuses the input; closing clears highlights and hides the keyboard.
## How it fits
Created by article/reader screens that host both a `WebView` and a `TextFinderLayout`; the layout supplies its widgets through `root()`, `input()`, `count()`, `back()`, `forward()`, `cancel()`. Keystrokes flow input -> `findAllAsync()` -> `setFindListener` count update, and navigation flows buttons -> `findNext()`.
## Key pieces
- `open()` — clears the input, shows the bar, and focuses the keyboard after a short delay. WHY: the delay lets the bar lay out before the keyboard claims the screen.
- `close()` — `clearMatches()` plus hide bar and keyboard. WHY: highlights must not linger after the UI is gone.
- Text watcher on `input` — calls `findAllAsync()` per keystroke. WHY: async search keeps typing smooth on long articles.
- `setFindListener` count update — formats "active of matches", coercing the active index to 0 when there are no matches. WHY: the platform reports a bogus active index of 1 with zero matches.
- Back/forward buttons — `findNext(false/true)` with the keyboard forced down. WHY: stepping through matches should not be covered by the keyboard.
- `isOpen()` — visibility of the bar root. WHY: lets the host decide whether back-press should close the finder or the screen.
## Junior notes
- The DONE keyboard action consumes the event and only hides the keyboard; search text stays, so the user can still step through matches.
- `findAllAsync()` on every keystroke can outpace rendering on huge pages; the platform serializes them, but avoid adding your own debounce that would delay the count.
