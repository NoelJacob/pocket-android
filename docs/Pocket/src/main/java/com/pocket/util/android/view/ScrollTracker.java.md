# Pocket/src/main/java/com/pocket/util/android/view/ScrollTracker.java
## What this is
A state machine that tells a `BaseWebView` (Pocket's customized WebView for article display) when scrolling starts, which direction it goes, when it overscrolls an edge, and when it has truly stopped. Stop detection works by re-invalidating (requesting redraws) and counting two draw passes with no scroll events, which filters out frame lag.
## How it fits
Owned by `BaseWebView`, which forwards its `onTouchEvent`, `onScrollChanged`, and `onDraw` into this tracker; `TouchShiftFrameLayout` only references its `DEBUG` flag for logging. Results flow out through `OnScrollListener` to article chrome (hide/show toolbars on direction change, overscroll effects). `isFlinging()` reports finger-up-but-still-moving state.
## Key pieces
- `ScrollTracker(view, directionChangeSlop)` — WHY: binds the host view and the minimum pixel travel before a direction flip counts. Usage in words: construct once per WebView with the system touch slop.
- `onTouchEvent(ev)` — WHY: tracks finger down/move/up, detects edge overscroll pulls, and kicks a final invalidate on release so stop detection runs. Call from the host's touch handler.
- `onScrollChanged(x, y, oldX, oldY)` — WHY: start-of-scroll detection plus direction-change detection with slop hysteresis (small wiggles ignored). Call from the host's scroll callback.
- `onDraw()` — WHY: the stop confirmer; two consecutive draws without an intervening scroll mean scrolling ended. Call from the host's draw.
- `OnScrollListener` (`onScrollStart` / `onScrollFinished` / `onScrollDirectionChanged(direction, isTouching)` / `onOverscroll(direction)` / `onScroll`) — WHY: the output contract; direction returns true to accept the flip, false to keep waiting.
- `setOnScrollListener(...)` / `isTouching()` / `isFlinging()` — WHY: wiring plus state queries for chrome logic.
## Junior notes
- Direction `-1` means up, `1` means down throughout; overscroll reuses the same convention.
- While the finger is down, stop detection pauses (state resets to SCROLLING); `onScrollFinished` only fires after lift plus two quiet draws.
- The commented-out `onScroll` invocation means per-pixel scroll callbacks never fire; only start/finish/direction/overscroll are live, so do not rely on `onScroll`.

