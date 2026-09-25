# pocket-ui/src/main/java/com/pocket/ui/text/CustomTypefaceSpan.java

## What this is

A `TypefaceSpan` (an Android span, i.e. a markup object attached to a slice of text that changes how that slice looks) that applies a real custom `Typeface` (a loaded font object) to part of a string. Stock `TypefaceSpan` only sets a font-family name, which often fails to resolve custom fonts; this class sets the actual `Typeface` on the paint used for drawing and measuring. It optionally synthesizes fake bold/italic when the underlying text style asks for more than the font file provides.

## How it fits

Producers like `IconFont` wrap an icon glyph in this span with `Fonts.get(context, Font.ICONS)` so the glyph renders in the icon font while surrounding text uses the normal font. Themed text views and databinding (XML layouts bound to ViewModel fields) adapters that need mixed fonts in one `TextView` also attach this span to a `SpannableStringBuilder` (a mutable rich-text buffer). Both `updateDrawState` and `updateMeasureState` apply the font so the text both measures and paints with the right metrics.

## Key pieces

- Constructors `CustomTypefaceSpan(type) / (type, allowFakeEffects) / (family, type)` — WHY: convenience overloads; the full `(family, type, allowFakeEffects)` version is the real one, and `family` is just passed to the `TypefaceSpan` superclass.
- `allowFakeEffects / mFakeEffectsEnabled` — WHY: controls whether missing bold/italic variants are faked (e.g. you supply a true italic font file, pass `false` so Android doesn't double-slant it).
- `updateDrawState / updateMeasureState` — WHY: both must apply the typeface or the text would measure with one font and draw with another, causing clipping or wrong line breaks.
- `applyCustomTypeFace(paint, tf, fakeEffectsEnabled)` — WHY: the core logic; compares the old paint style against the new typeface style (`fake = oldStyle & ~tf.getStyle()`), sets fake-bold (`setFakeBoldText`) or fake-italic (`setTextSkewX(-0.25f)`) only for the missing bits, then calls `paint.setTypeface(tf)`.

## Junior notes

- A span only affects the character range you attach it to via `setSpan(...)`; creating the span alone does nothing.
- If you pass a bold or italic font file, pass `allowFakeEffects = false`, otherwise Android will synthesize an extra bold/slant on top of the real one.
