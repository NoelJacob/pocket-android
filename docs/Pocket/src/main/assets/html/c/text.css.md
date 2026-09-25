# Pocket/src/main/assets/html/c/text.css

## What this is

This is the base stylesheet for article view, about 760 lines. It styles the readability article (`#RIL_container`, `#RIL_header`, images, tables, code) and implements theming entirely through attributes on the `body` tag: `textStyle` picks light or dark colors, `fontType` picks one of ten reader fonts (Blanco, Graphik, IdealSans, Inter, IBMPlexSans, Sentinel, Tiempos, Vollkorn, Whitney, ZillaSlab), and `lineHeightSetting` picks the line spacing. The body starts `visibility: hidden` so the page stays blank until native settings are applied.

## How it fits

Loaded first by all three `article-mobile*.html` shells, with `c/text-tablet.css` and `c/highlighting.css` layered over it. The `body` attributes it keys on are set from native display settings through `ArticleView.load()` and the `newFontSize` / `newFontType` / `newTextAlign` / `newLineHeightSetting` / `newTextStyle` methods in `j/articleview-mobile.js`, which `ArticleFragment` and `ArticleViewModel` trigger. Entries: theme color variables and link styling, per-font families and sizes, per-setting line heights, article container, header, image, table, and gallery rules.
