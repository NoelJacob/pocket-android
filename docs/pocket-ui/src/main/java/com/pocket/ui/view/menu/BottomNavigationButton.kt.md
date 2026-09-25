# pocket-ui/src/main/java/com/pocket/ui/view/menu/BottomNavigationButton.kt

## What this is
A single bottom-tab button: an icon stacked over a text label, used in the app's tabbed navigation bar. It is checkable, so one tab appears selected (highlighted) while the others look dimmed. It can also show a small badge dot for unread/new state.

## How it fits
Used by the bottom navigation bar layout — each tab (e.g. My List, Discover, Search) is one of these buttons. Created from XML with the `imageSrc` and `labelText` attributes (see `R.styleable.BottomNavigationButton`). Tapping a tab calls `setChecked(true)` on it (via `CheckableHelper`), which recolors the icon/label and swaps the label font; `badgeVisible` toggles the badge dot, bindable from XML layouts via databinding (databinding = XML layouts bound to ViewModel fields) with `app:badgeVisible` and `app:isChecked`.

## Key pieces
- `BottomNavigationButton` — the view itself; extends `ThemedConstraintLayout2` (a theme-aware layout that re-tints itself when light/dark mode changes) and implements `CheckableHelper.Checkable`.
- `checkable` — a `CheckableHelper` delegate holding the checked state and firing change listeners; `setCheckable`/`toggle`/`isChecked` all forward to it.
- `binding` (`ViewBottomNavigationButtonBinding`) — inflated layout holding `icon`, `label`, `badge`, and `root`; avoids manual `findViewById`.
- `badgeVisible` — property showing/hiding the badge dot view.
- `setChecked(checked)` — the visual core: swaps label typeface (medium when selected, regular when not) and tints icon/label grey-1 (selected) vs grey-3 (unselected).
- `setContentDescription` override — also pushes the description into `TooltipCompat` so long-press shows a tooltip.
- `companion object` binding adapters — `isChecked` and `badgeVisible` adapters so XML databinding expressions can drive state directly.

## Junior notes
- `Checkable` here is Android's checkable contract (checked/unchecked, like a checkbox) applied to a tab — `CheckableHelper` is Pocket's reusable delegate for it, so you never store `isChecked` in the view yourself.
- Constructor reads custom XML attributes via `obtainStyledAttributes` — always paired with `recycle()` to release the shared attribute array.
