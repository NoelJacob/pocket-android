# Pocket/src/main/java/com/pocket/sdk/tts/ArticleUtteranceParser.java
## What this is
This turns a saved article's offline HTML file into a list of speakable text chunks (`Utterance` objects) for on-device text-to-speech. It reads the article-view HTML the app already downloaded for offline reading, pulls out the title, author, date, and body text, and splits long blocks so no single chunk exceeds `MAX_UTTERANCE_CHARACTER_LENGTH` (3000 chars, a ceiling imposed because TTS engines reject very long strings).
## How it fits
`TTSPlayer.load()` creates an `ArticleUtteranceParser` for the article URL and calls `parse()`. Parsing runs on a background thread (a coroutine-like worker; coroutines = background tasks that don't block the UI). On success it returns an `ArticleTTSUtterances` bundle that `TTSPlayer` queues into Android's `TextToSpeech` engine sentence by sentence; on failure it reports a `ListenError` (article not downloaded, or parsing failed) that `Listen` surfaces as player state. It mirrors the JavaScript parsing in `assets-extra/j/articleview-mobile.js`, so web and native stay consistent.
## Key pieces
- `parse()`: the only public entry point. Guards against double-start, ensures the article HTML is downloaded offline, then parses on a worker thread and posts the result back on the UI thread. WHY async: file IO plus HTML parsing is too slow for the main thread.
- `parseNode(element)`: recursively walks the article body DOM, skipping `script`, `style`, and `pktnolisten` elements, and grouping child nodes into single speech blocks or individual utterances. WHY recursion: article HTML nests unpredictably.
- `addUtterance(...)` overloads: normalize one text node or element (trim, clean quotes, attach a `NodeSelector` locator and node index) and append it to the speech list. Long text is split into numbered segments here.
- `ArticleTTSUtterances`: the result bundle — ordered utterance list, node-index lookup map, title/author/url, total character length. `TTSPlayer` uses it for playback order, seeking by node, and progress math.
- `OnParsedListener`: callback with `onArticleUtterancesParsed` / `onArticleUtterancesParserError`. WHY a callback instead of a return value: parsing spans threads and a download check.
- `cleanText()`: strips extra whitespace and stray single quotes so the TTS voice doesn't read punctuation aloud.
## Junior notes
- `nodeIndex` is the `nodeIndex` HTML attribute the article view embeds. It links a spoken chunk back to the highlighted paragraph on screen; 0 means no link.
- `mPendingNodeIndexes` collects parent indexes while descending so a split-apart element still maps to one utterance later. `flushPendingNodeIndexes()` must run after the body walk or highlighting breaks.
- This is a Java port of JS logic. If the article view markup changes, update both this file and `articleview-mobile.js` together.
