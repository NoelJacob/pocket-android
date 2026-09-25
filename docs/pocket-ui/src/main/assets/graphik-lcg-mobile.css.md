# pocket-ui/src/main/assets/graphik-lcg-mobile.css

## What this is

The `@font-face` binding that makes Pocket's Graphik LCG interface font available to article WebViews as `font-family: 'GraphikLcg'` in normal/italic × regular/bold. It points at the metric-fixed `*_no_leading.otf` files (see `pocket-ui/src/main/assets/README.md` for why the leading was removed).

## How it fits

Consumed by the reader article templates (`Pocket/src/main/assets/html/article-mobile*.html`) via `<link href="../graphik-lcg-mobile.css">`: headlines and UI text inside saved articles render through this sheet. Like its Blanco sibling, it degrades to system fonts when the licensed `.otf` files aren't installed locally.
