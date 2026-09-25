# Pocket/src/main/java/com/pocket/util/android/view/ManuallyUpdateTheme.java
## What this is
A three-method interface for views that cannot theme themselves with state selectors (XML color rules) alone, such as WebViews. Implementing views register with the activity's theme system and repaint themselves when the theme flips between light and dark.
## How it fits
Implemented by `com.pocket.util.android.webkit.BaseWebView`; `com.pocket.sdk.util.AbsPocketActivity` keeps a list of weak references (`mViewsListeningForThemeChanges`) and calls `updateThemeManually()` on each when the theme changes. It produces themed view state downstream of the activity's theme broadcast.
## Key pieces
- `registerForThemeUpdates()` — WHY: enrollment; the view tells the `Theme` it wants manual callbacks. Usage in words: call once during view init, then call `updateThemeManually()` immediately to paint the initial state.
- `updateThemeManually()` — WHY: the repaint hook the activity invokes on every theme change.
- `getContext()` — WHY: gives the theme system a context for resolving themed resources; every `View` already has one.
## Junior notes
- The activity holds weak references, so a view that never unregisters will simply be garbage collected; no explicit unregister call exists.
- Always paint initial state in init too; registration alone does not trigger a first `updateThemeManually()`.

