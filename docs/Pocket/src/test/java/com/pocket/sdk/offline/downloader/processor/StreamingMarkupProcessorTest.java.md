# Pocket/src/test/java/com/pocket/sdk/offline/downloader/processor/StreamingMarkupProcessorTest.java
## What this is
Tests for `StreamingMarkupProcessor`, which saves web pages for offline reading by rewriting HTML/CSS asset references to local files. `processHtml` feeds a fixture page and asserts stylesheets, images, and absolute URLs are collected (and a clean/remove URL is not), with UTF-8 detected; `processStylesheet` covers CSS rewriting.
## How it fits
Guards production `StreamingMarkupProcessor`, used by the offline downloader pipeline writing into a temp processing dir and output file. JUnit4 test with a `TemporaryFolder` rule, fixture `StreamingMarkupProcessorTest/example.html`, and Okio sources.
## Key pieces
- `processHtml()` — given expected/not-expected URL lists and a 10 MB budget, processes the fixture, asserts `HtmlSuccess` charset `UTF-8`, all expected assets consumed, excluded URL absent from output; WHY: end-to-end rewrite contract for a page.
- `processStylesheet()` — same idea for a CSS file's nested references; WHY: stylesheets carry their own asset graph.
- `TemporaryFolder tmp` (`@Rule`) — fresh dirs/files per test; WHY: processor writes real files, isolated per run.
## Junior notes
- The `expected` list is mutated (consumed) by the literal callback during processing — `assertTrue(expected.isEmpty())` means every asset was seen.
- Full-parser conformance lives in external parser-fixtures; this file covers basic cases only, per its own javadoc.
