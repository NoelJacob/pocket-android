# Pocket/src/main/java/com/pocket/util/KeyboardUtil.kt

## What this is
A one-line View extension, `hideKeyboard()`, that dismisses the soft (on-screen) keyboard for whatever window the view is attached to. It solves the "keyboard stays up after the user is done typing" problem without each screen touching `InputMethodManager` directly. For example, `TagBottomSheetFragment` calls `binding.root.hideKeyboard()` when the user taps Save or Cancel, and `MyListFragment` calls it when search is submitted or cleared.

## How it fits
It lives in generic `com.pocket.util` and is called from any screen or bottom sheet that takes text input. Known callers include `MyListFragment`, `TagBottomSheetFragment`, `ReportItemBottomSheetFragment` (on a HideKeyboard event), and `ArticleFragment` (when closing find-in-page). It produces nothing downstream; it just asks the system input service to hide the keyboard for the view's window token.

## Key pieces
- `hideKeyboard()`: Kotlin extension on `View` that looks up `INPUT_METHOD_SERVICE` and calls `hideSoftInputFromWindow(windowToken, 0)`. WHY it exists: one shared, null-safe spelling so screens never hand-roll input-manager code.

## Junior notes
- The safe-cast `as?` plus `?.` chain means this silently does nothing when there is no input manager or no context — hiding the keyboard is best-effort, never crash-worthy.
- It must be called on a view attached to a window (it uses `windowToken`); calling it on a detached view is a harmless no-op.
- The counterpart for showing the keyboard lives elsewhere (`showKeyboard` in the `android` util package, also used by `MyListFragment`).
