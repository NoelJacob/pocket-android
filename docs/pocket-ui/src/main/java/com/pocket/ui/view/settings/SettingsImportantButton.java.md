# pocket-ui/src/main/java/com/pocket/ui/view/settings/SettingsImportantButton.java
## What this is
A full-width settings row for dangerous or standout actions such as Log Out: a large divider with red link-style text. It is a button disguised as a settings row, visually separated from the normal rows above it.

## How it fits
Settings screens place it at the bottom of the list (layout view_settings_important) for the one destructive action. It extends ThemedConstraintLayout and exposes a tiny Binder: `text(...)` sets the label, `clear()` resets it. Taps are handled by whatever click listener the screen attaches.

## Key pieces
- `init()` — inflates view_settings_important and grabs the R.id.text label.
- `bind()` / inner `Binder` — `text(CharSequence)` sets the label; `clear()` nulls it.

## Junior notes
- The red styling lives in the layout/theme resources, not in this class; code only sets the text.
- Like other pocket-ui rows, configuration goes through the Binder rather than exposing the TextView.
