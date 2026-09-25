# pocket-ui/src/main/java/com/pocket/ui/text/TextViewUtil.java

## What this is

A static helper for `TextView` (Android's basic text-display and editing widget) edge cases. It provides a lowercasing `InputFilter` (an object that transforms text as it is typed) that preserves rich-text spans, padding helpers that compensate for font-metric whitespace so text looks optically centered, and a height-prediction method that measures how tall a block of text will render before layout.

## How it fits

EditTexts that must force lowercase (e.g. email/username fields) set `AllLowerCase` as their filter instead of the platform `AllCaps` variant, avoiding the third-party-keyboard text-duplication bugs described in its javadoc. Screens with tight pill/badge/chip layouts call `setVisualTextPadding` or `verticallyCenterPadding` after setting the typeface and size so the visible glyphs (not the font's invisible ascent/descent box) align with the design. Code that sizes a container ahead of layout calls `getExpectedTextViewHeight` with the paint, text, and width to pre-compute the pixel height.

## Key pieces

- `AllLowerCase` — WHY: lowercases input while copying spans (`TextUtils.copySpansFrom`) onto the lowered text; returns null when nothing changed so the original (with its spans intact) is kept. Modeled on `InputFilter.AllCaps` but inverted.
- `CharSequenceWrapper` — WHY: private `CharSequence`/`Spanned` view over just the `[start, end)` filter window, copied from `AllCaps`, so codepoint scanning never reads past the edited region.
- `setVisualTextPadding(view, resIds)` / `(view, left, top, right, bottom)` — WHY: subtracts the font's built-in top (`fm.top - fm.ascent`) and bottom (`fm.descent`) slack from the requested padding so equal XML padding looks visually equal for any typeface.
- `verticallyCenterPadding(view)` — WHY: rewrites only top/bottom padding so the ascent and descent whitespace match, optically centering a single line without touching horizontal padding.
- `ascent / descent / bottom` — WHY: thin readers over `Paint.FontMetrics` (the font's per-size vertical metrics); note `ascent()` returns `|fm.ascent - fm.top|`, i.e. the extra top slack, not the raw ascent.
- `getExpectedTextViewHeight(paint, text, alignment, viewWidthPx, lineHeightPx)` — WHY: builds a throwaway `StaticLayout` (Android's multi-line text measurer) with the extra line spacing and returns its height, for pre-layout sizing.

## Junior notes

- Font metrics reminder: `top/ascent` are negative offsets above the baseline, `descent/bottom` positive below it — `bottom >= descent` always, since `bottom` includes extra line-gap room.
- `getExpectedTextViewHeight` uses the deprecated `StaticLayout` constructor; it still works but will need the `Builder` API if the project raises its min SDK past the deprecation.
