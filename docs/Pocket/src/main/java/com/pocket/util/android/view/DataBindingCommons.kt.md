# Pocket/src/main/java/com/pocket/util/android/view/DataBindingCommons.kt
## What this is
A set of XML data-binding adapters (functions that let layout XML attributes drive view code: `app:visibility="@{...}"` calls a Kotlin function). They cover boolean visibility, string-resource text, underline toggling, markdown rendering, drawable/tint/color binding by resource id.
## How it fits
Applied implicitly by Android databinding (XML layouts bound to ViewModel fields) wherever layouts use these custom attributes; `textMarkdown` renders `MarkdownFormatter` output into `ThemedTextView`s (theme-aware text views) using `App::viewUrl` for link taps. These are leaf setters with no downstream calls beyond formatting and resource lookup.
## Key pieces
- `setVisibility(view, visible)` (`visibility`) — WHY: boolean-to-`VISIBLE`/`GONE` so layouts avoid converters. Usage in words: bind `app:visibility="@{vm.show thing}"`.
- `setText(view, textId)` (`textId`) — WHY: binds a string resource id directly instead of resolving it in code.
- `setTextUnderline(view, enabled)` (`textUnderline`) — WHY: toggles the paint underline flag for link-like text.
- `setTextMarkdown(view, markdown)` (`textMarkdown`) — WHY: renders markdown copy into themed text with working links.
- `setDrawable(view, drawableId)` (`drawableId`) — WHY: swaps an `ImageView`'s drawable from an id.
- `setTextColor(view, textColorId)` (`textColorId`) / `setTintColor(view, tintColorId)` (`tintColorId`) — WHY: theme-friendly color/tint binding from color-state-list ids.
## Junior notes
- The `visibility` adapter maps false to `GONE` (removes from layout), not `INVISIBLE` (keeps space); use manual code if you need the latter.
- `textMarkdown` requires a `ThemedTextView`, not a plain `TextView`; using it on the wrong type fails binding at runtime.
- `textUnderline` mutates `paintFlags` bit-wise, preserving other flags like strike-through.

