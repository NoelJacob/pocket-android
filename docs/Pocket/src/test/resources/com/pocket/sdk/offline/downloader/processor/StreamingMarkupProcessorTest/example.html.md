# Pocket/src/test/resources/com/pocket/sdk/offline/downloader/processor/StreamingMarkupProcessorTest/example.html

## What this is

An HTML fixture covering the messy markup the offline article processor must survive: stylesheet links, inline `@import` styles, and images/attributes in every casing and shape (uppercase `SRC`, stray whitespace, `background` on `input`/`table`/`td`/`p`). Like its CSS sibling, it is adversarial input for the parser, not a real article.

## How it fits

Read by `StreamingMarkupProcessorTest` (HTML test) via `getResourceAsStream("StreamingMarkupProcessorTest/example.html")` and fed to `StreamingMarkupProcessor.processHtml()`, which extracts cachable resources, records the encoding, and writes the rewritten page. Entry inventory: `<link href="yay.css">` (external stylesheet), inline `<style>` with the four `@import` forms plus a `url()` background, `something.jpg/2/3.jpg` (normal, uppercase-attr, and whitespace-broken `img` tags), `background` attributes with absolute, root-relative, and bare URLs.
