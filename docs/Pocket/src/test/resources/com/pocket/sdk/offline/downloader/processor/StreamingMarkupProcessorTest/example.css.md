# Pocket/src/test/resources/com/pocket/sdk/offline/downloader/processor/StreamingMarkupProcessorTest/example.css

## What this is

A five-line CSS fixture exercising every URL-rewriting path of the offline stylesheet processor: bare, parent-relative, absolute, and `url()`-wrapped `@import`s plus a `url()` image reference. It is deliberately adversarial input, not real styling.

## How it fits

Read by `StreamingMarkupProcessorTest` (stylesheet test) via `getResourceAsStream("StreamingMarkupProcessorTest/example.css")` and fed to `StreamingMarkupProcessor.processStylesheet()`, which rewrites each URL for offline caching and asserts success. Entry inventory: `@import 'c22.css'` (bare relative), `@import "../1/c23.css"` (parent-relative), `@import 'https://parser-fixtures.readitlater.com/1/c24.css'` (absolute, stays remote), `@import url("../1/c25.css")` (wrapped relative), `.c6 { background: url(6.png); }` (resource URL to cache).
