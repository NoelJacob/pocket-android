# pocket-ui/src/main/java/com/pocket/ui/text/Fonts.java

## What this is

The central registry for Pocket's custom fonts. Its `Font` enum maps logical names to asset filenames: Graphik (the sans-serif UI font: regular, regular-italic, medium, medium-italic, bold), Blanco (the serif article font: regular, bold, italic, bold-italic), Doyle (one serif display cut, medium), and ICONS (the `pocket_icons.ttf` glyph font). `get()` loads a `Typeface` once and caches it in memory; callers use it from code, from XML via the `app:typeface` attribute, or from article CSS via `GRAPHIK_CSS`.

## How it fits

`ThemedTextView` and `ThemedEditText` read the `app:typeface` XML attribute and call `get(context, attrValue)` to resolve the integer enum value to a font. Code that styles part of a string (e.g. `IconFont` for inline icons, databinding — XML layouts bound to ViewModel fields — adapters and `CustomTypefaceSpan` users) calls `get(context, Font.XXX)`. Compose typography calls `Graphik(assets)` in `PocketTypography.kt`, which reads the same `Font....filename` values. WebViews (embedded browser views rendering article HTML) include the `graphik-lcg_mobile.css` asset named by `GRAPHIK_CSS`.

## Key pieces

- `Font` enum with `(attrValue, filename)` — WHY: one table tying three identities together: the XML `typeface` attribute integer, the asset filename, and the logical name. Graphik entries point at `graphik_lcg_*_no_leading.otf`; Blanco at `blanco_osf_*.otf`; Doyle at `doyle_medium.otf`; ICONS at `pocket_icons.ttf`.
- `get(context, int attrValue)` — WHY: the XML path; loops over `Font.values()` matching `attrValue` and returns null when nothing matches so unknown attributes degrade gracefully.
- `get(context, Font font)` — WHY: the code path; checks the static `cache` map first so each font file is parsed only once, then delegates to `systemTypeface()` and caches the result.
- `systemTypeface(context, font)` — WHY: the actual loader. Only `ICONS` still loads its bundled file via `Typeface.createFromAsset` (it ships in-repo); Graphik maps to `sans-serif` normal/italic/bold and Blanco/Doyle map to `serif` normal/italic/bold because the proprietary `.otf` files are secrets-gated and absent from the repo. The `ponytail:` comment on `get()` marks this substitution.
- `GRAPHIK_CSS = "graphik-lcg_mobile.css"` — WHY: the filename WebViews must include so CSS `font-family` names line up with the native fonts.
- `cache` static map — WHY: avoids re-parsing font data on every `TextView`; the class comment notes it is UI-thread-only and never cleared.

## Junior notes

- The class javadoc explains why fonts live in `assets/` instead of `res/font/`: WebViews and some devices (plus `ResourcesCompat.getFont` bugs) made `android_res` URLs unreliable, while assets work from code, XML, and HTML/CSS alike.
- `get(context, int)` returns `@Nullable` (it can return null); the `Font` overload never returns null — it falls back to a system family.
