# Pocket/src/main/java/com/pocket/sdk/util/MarkdownFormatter.kt
## What this is
Renders Markdown text into styled Android text for display. It configures the Markwon library with Pocket's brand fonts and a custom link-tap handler, then exposes a single format call.
## How it fits
Instantiated by screens showing rich text (e.g. upsell or help copy) with a Context and an onLinkClicked callback. Produces a CharSequence with spans that a TextView displays; link taps route to the caller's handler instead of a browser default.
## Key pieces
- `markwon`: Markwon instance with a plugin overriding Emphasis (italic) and StrongEmphasis (bold) spans to Graphik LCG typefaces via CustomTypefaceSpan.
- `configureConfiguration` linkResolver: intercepts every link tap and forwards it to onLinkClicked.
- `format(markdown)`: converts a Markdown string to styled text in one call.
## Junior notes
- Markwon = Markdown-to-Android-Spanned library; spans are style ranges (bold, italic) applied to text. CustomTypefaceSpan swaps the font for those ranges.
- Needs an Activity/Context for font lookup; do not hold it longer than the screen to avoid leaking the Activity.
