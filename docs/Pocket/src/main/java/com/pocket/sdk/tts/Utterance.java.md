# Pocket/src/main/java/com/pocket/sdk/tts/Utterance.java
## What this is
This is one speakable chunk of an article: a sentence or paragraph of text plus everything needed to speak it, highlight it, and track progress through the article. `ArticleUtteranceParser` produces these; `TTSPlayer` consumes them in order.
## How it fits
The parser splits article HTML into an ordered `Utterance` list bundled as `ArticleTTSUtterances`. `TTSPlayer` queues each chunk's `text` into Android's `TextToSpeech` engine (the OS service that synthesizes speech from text) using `params` (which carries the utterance ID), publishes the started chunk in `ListenState.utterance` for highlighting and progress, and uses `endPosition` for save/resume math. The article view resolves `jQuerySelector`/`nodeIndex` to scroll the matching paragraph into view.
## Key pieces
- `text`: the literal string handed to the TTS engine. Already cleaned (whitespace/quotes normalized) by the parser; never empty for a valid utterance.
- `jQuerySelector / nodeIndex`: the two halves of the highlight link — a `NodeSelector` (CSS selector plus child index) for the element and the Pocket paragraph index (0 = none). Either may be null/0 in edge cases, meaning "speak without highlighting".
- `isHeader`: marks titles and headings so the player can insert longer surrounding silence. WHY a flag instead of styling inference: the player shouldn't parse HTML to decide pacing.
- `endPosition`: cumulative character offset at the end of this chunk. Progress percent and resume positions derive from this, not from audio clocks (device TTS has no exact clock).
- `segmentIndex / position / isSegment()`: long nodes split to fit the engine get `segmentIndex >= 0` and a global `position` used as the TTS utterance ID. `position` order is stable and safe to cache for seeking.
- `params`: the engine parameter map carrying `KEY_PARAM_UTTERANCE_ID`. That ID is what links the OS "done speaking #7" callback back to this object.
## Junior notes
- Fields are all `final` (set once in the constructor). Utterances are effectively immutable; seeking builds a new queue position rather than editing chunks.
- `position` doubles as the TTS utterance ID string. Reordering or filtering the list after IDs are issued breaks the done-callback mapping — treat the list as append-only once playback starts.
- Streaming mode (`GetItemAudioPlayer`) never creates these; code that assumes `ListenState.utterance` is non-null crashes on server audio. Always null-check.
