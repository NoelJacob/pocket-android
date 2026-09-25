# pocket-ui/src/main/java/com/pocket/ui/view/edittext/TextFinderLayout.java

## What this is
A small contract (Java interface) naming the six parts every find-in-page bar must expose: the bar itself, a cancel button, the search input, the match-count label (like "3 of 12"), and the previous/next match arrows. It says nothing about looks or behavior; it just lets controller code treat different find-bar implementations identically.

## How it fits
Sits between find-in-page UI and its driver: TextFinderView implements this interface by returning views from its inflated `view_text_finder` binding, and reader/webview code drives the search (query text, match navigation, dismiss) through these accessors instead of knowing the concrete view class. Any future find-bar redesign only needs to implement these six methods to plug into the same flow.

## Key pieces
- `root()`: the whole bar view, for showing/hiding the bar itself.
- `cancel()`: the dismiss button that closes the find session.
- `input()`: the EditText carrying the search query; drivers attach text listeners here.
- `count()`: the TextView showing match position ("N of M") or no-match state.
- `back()` / `forward()`: the up/down arrows stepping through matches.
- No databinding adapters or XML attributes: this is a pure interface with zero implementation.

## Junior notes
- An interface in Kotlin/Java is a promise of methods with no body; `TextFinderView : ThemedConstraintLayout, TextFinderLayout` means it IS-A layout that ALSO promises these six parts.
- If you add a part to the find bar (for example a clear-query button), add it to the concrete view only; changing this interface breaks every implementer and driver.
- Nullability and lifecycle are the caller's problem: drivers must handle the bar being hidden (root GONE) while listeners on `input()` are still attached.
