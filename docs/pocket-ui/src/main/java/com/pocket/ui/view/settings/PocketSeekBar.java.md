# pocket-ui/src/main/java/com/pocket/ui/view/settings/PocketSeekBar.java
## What this is
Pocket's custom-styled slider: a pill-shaped track (grey) with a pill progress fill and a round thumb (filled circle with a thin outline ring). It is the slider users drag in settings such as text size or playback speed.

## How it fits
Settings screens drop this in wherever a numeric preference needs a slider; it extends ThemedSeekBar so the theme state flows into its drawables. `init()` wires a custom thumb (Handle) and a two-layer progress drawable (track + clipped progress). `onDraw` syncs the clipped progress layer's bounds and theme state before delegating to the stock SeekBar drawing. All paint colors resolve from theme-aware color state lists.

## Key pieces
- `init()` — sizes thumb/track from dp, installs the Handle thumb, builds the LayerDrawable (track plus ClipDrawable-wrapped progress), and pads the ends so the thumb stops flush with the track.
- `getRealProgressDrawable()` — pulls the inner progress layer out of the LayerDrawable; null-safe for early calls before construction finishes.
- `onDraw()` — syncs bounds and drawable state onto the progress layer so the fill length and colors stay correct, then draws normally.
- `Progress` — pill-shaped fill/track drawable using PillPath and a theme-aware color list.
- `Handle` — the round thumb: outer stroke circle plus smaller fill circle, sized to thumb radius plus shadow padding.
- `ThemeDrawable` — base class that fans out alpha, color filter, and state changes to every registered Paint; subclasses implement `updatePaints()`.
- `PillPath` — builds a pill outline (rectangle plus half-circle caps, or a circle when square) reused for track and progress.

## Junior notes
- Drawables here are stateful (`isStateful()` true): theme switches arrive as state changes, and `updatePaints` re-resolves colors, so no manual refresh is needed.
- `setBackgroundDrawable(null)` removes the default ripple; the thumb offset plus matching padding keeps the thumb from overhanging the track ends.
