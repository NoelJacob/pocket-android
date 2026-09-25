# pocket-ui/src/main/java/com/pocket/ui/view/button/IconButton.java

## What this is
The standard tappable icon of the old View-system UI: for the user a 50dp-square touch target showing a smaller glyph (back arrow, close X, share, favorite, overflow dots), with a tooltip naming the action on long-press. It extends `CheckableImageView` (an ImageView that also supports a checked on/off state and Pocket's theme-aware drawable tinting), so icons automatically re-tint for light/dark theme and for checked states like saved/favorited.

## How it fits
Used across toolbars, list rows, and dialogs: `AppBar` creates them for action icons (wrapping new ones in the `Pkt_IconButton` style), `RadioButton` and `CheckBox` subclass it for toggle icons, and many layouts reference the `Pkt_IconButton_*` styles (Up, Close, Share, Favorite, Save, Archive, Listen, Premium…) defined in `styles.xml`. The default style comes from the `iconButtonStyle` theme attribute, so every `IconButton(context, attrs)` picks up the app-wide icon treatment unless overridden. There are no databinding adapters here — layouts configure icons with stock attributes (`srcCompat`, `contentDescription`) plus the one custom attribute below.

## Key pieces
- `init(attrs)` — reads the single custom XML attribute `checkedDrawableColor` and installs a drawable-color override; WHY it exists: lets one layout instance recolor only its checked state (e.g. amber stars, coral saves) while keeping the shared style for everything else. The override applies only when the view is enabled and checked.
- `setContentDescription(...)` — forwards the description to `TooltipCompat.setTooltipText`; WHY: stock ImageView speaks the description to screen readers but shows nothing visually — this one line adds the long-press tooltip sighted users expect.
- `setSideMarginStart() / setSideMarginEnd()` and `setVisualMarginStart/End(margin)` → `setIconSideMargin(...)` — WHY they exist: the 50dp touch target is wider than the visible glyph, so an icon pinned to a screen edge looks inset; these helpers shift padding so the *visible* art aligns to the `pkt_side_grid` (or a custom dimen) while keeping the full touch area.
- Style family `Pkt_IconButton` + `Pkt_IconButton_<Name>` in `styles.xml` — the real "API" for adding a new toolbar icon: content description plus `srcCompat` drawable, optionally `isCheckable` + checked tint. Most new icons need only a style entry, not a new class.

## Junior notes
- `CheckableImageView` tinting is driven by `drawableColor` / state lists — setting `android:tint` directly in a layout fights the theming; use `drawableColor` or the checked-color attribute instead.
- Coroutines (background tasks) and `StateFlow` (observable state streams) don't appear here: checked/pressed visuals are synchronous view-state changes, not data streams — the parent fragment observes data and calls `setChecked()`.
