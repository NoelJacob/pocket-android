# pocket-ui/src/main/java/com/pocket/ui/view/button/PocketIcons.kt

## What this is
A tiny library of named Compose icons: `UpIcon` (back/up arrow) and `OverflowMenuIcon` (three-dot menu). For the user these are just the familiar toolbar glyphs. For developers they are one-line composables (UI-building functions) that bundle the right drawable art with the right accessibility string, so no screen hard-codes drawable or string resource ids for these two icons.

## How it fits
Content slots for `PocketIconButton` — e.g. the Compose `AppBar` preview renders `PocketIconButton { UpIcon() }`. Each function loads art with `painterResource` (Compose's drawable loader) and the spoken/long-press label with `stringResource` (`ic_up`, `ic_overflow`), rendering a Material3 `Icon` (a component that draws a single glyph with the current content tint).

## Key pieces
- `UpIcon(modifier)` — back-arrow glyph (`ic_pkt_back_arrow_line`) + `ic_up` label; WHY it exists: every "navigate up" affordance shares one definition, so art/label never drift between screens.
- `OverflowMenuIcon(modifier)` — three-dot glyph (`ic_pkt_android_overflow_solid`) + `ic_overflow` label; same rationale for the overflow-menu affordance.
- `modifier` parameter on both — pass-through for sizing/positioning from the caller; the icon itself adds no styling.

## Junior notes
- Need a third shared icon? Add it here following the same pattern (drawable + string + one function) rather than inlining `Icon(painterResource(...))` at the call site.
- These intentionally don't set a tint — they inherit `LocalContentColor` from the surrounding `PocketIconButton`, which is what keeps light/dark theme behavior consistent.
