# Pocket/src/main/java/com/pocket/sdk/tts/NodeSelector.java
## What this is
This is a locator that points at one exact sentence inside the saved-article HTML so Listen can highlight the words as they are spoken. It stores a jQuery/CSS selector for the containing element plus, for raw text nodes, the child index within that element.
## How it fits
`ArticleUtteranceParser` builds one `NodeSelector` per `Utterance` while parsing the offline article HTML. `TTSPlayer` carries the current utterance (with its selector) in `ListenState`, and the article view uses the selector to scroll to and highlight the matching node — the familiar karaoke-style follow-along. Streaming mode has no per-sentence chunks, so selectors only matter for on-device TTS.
## Key pieces
- `getNew(TextNode) / getNew(Element)`: factories that compute a selector for a text node (selector of its parent plus child index) or an element. Return null when nothing addressable exists, which callers must tolerate as "no highlighting for this chunk".
- `mElement / mIndex`: the two halves of the locator. `mElement` is the CSS selector; `mIndex` of -1 means the selector points at the element itself, otherwise at the Nth child node inside it.
- `getJQuerySelector(element)`: walks up the DOM building a chain like `#id :eq(2) :eq(0)` until it hits an anchor with a unique selector. WHY bottom-up: nearby indexes are compact and robust to distant page changes.
- `getUniqueSelector(element)`: prefers the Pocket `nodeIndex` attribute, then element `id`, then `body`/`html` tags as anchors. WHY that order: `nodeIndex` is the stable Pocket-assigned paragraph ID, while raw tag paths break on any markup edit.
- `indexInParent(Element/TextNode)`: positional disambiguation among siblings. Element children versus all child nodes are counted differently because text and tags interleave.
## Junior notes
- Selectors are evaluated in the article WebView with jQuery semantics (`:eq()` is zero-based). Testing a selector means running it against the actual offline HTML, not eyeballing the markup.
- A null selector is legal and means "speak but don't highlight". Never crash on it; skip the highlight for that utterance.
- Internal builds throw on unresolvable elements (fail fast for developers) while production returns null. A crash here in a dev build points at article markup the parser didn't expect.
