# pocket-ui/src/main/assets/blanco-mobile.css

## What this is

The `@font-face` binding that makes the Blanco serif family available to article WebViews as `font-family: 'Blanco'` in normal/italic × regular/bold. It maps each variant to its (git-ignored, licensed) `.otf` file; the stale `file:///android_res` comment at the top records the previous loading mechanism.

## How it fits

Consumed by the reader article templates (`Pocket/src/main/assets/html/article-mobile*.html`) via `<link href="../blanco-mobile.css">`: article body/serif text in the offline reader renders through this sheet. If the `.otf` files are absent (fresh clone), this sheet silently does nothing and the WebView falls back to its default serif.
