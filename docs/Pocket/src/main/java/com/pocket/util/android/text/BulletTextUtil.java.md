# Pocket/src/main/java/com/pocket/util/android/text/BulletTextUtil.java
## What this is
A one-method helper that turns an array of text lines into a properly indented bulleted list for a `TextView`. It joins the lines with newlines and stamps each with Android's `BulletSpan` (a span object that draws the dot and indent).
## How it fits
Used by `com.pocket.sdk.offline.cache.StorageLocationPickerDialog`, which lists offline-save location details (in words: `makeBulletList(dpToPx(5), bullets)`). It consumes plain `CharSequence`s and produces a `SpannableStringBuilder` (mutable styled text) ready for `textView.text = ...`.
## Key pieces
- `makeBulletList(leadingMargin, lines...)` — WHY: the whole feature; one call replaces manual bullet characters and padding math. Usage in words: pass the gap in pixels and one string per bullet, set the result as the TextView's text.
## Junior notes
- `leadingMargin` is pixels, not dp; callers must convert (the picker uses `FormFactor.dpToPx(5)`).
- Each line gets its own `BulletSpan` over `SPAN_INCLUSIVE_EXCLUSIVE`; appending pre-styled text keeps inner styles (bold/links) intact.

