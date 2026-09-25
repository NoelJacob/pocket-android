# Pocket/src/main/java/com/pocket/sdk2/view/ModelBindingHelper.kt
## What this is
A Hilt `@Singleton` formatting helper that turns raw `Item` data (a saved article: title, URL, reading progress, word count) into display strings for list rows and the reader: read-time estimates ("5 min"), titles with search highlighting, and domain names with highlighting. It centralizes the plural-string and highlight logic so every screen renders estimates and search matches identically. It has no UI code itself — it returns strings.
## How it fits
Injected into view holders, databinding adapters (XML layouts bound to ViewModel fields), and search-result binders. Time methods pull durations from `ItemUtil` (word-count → `Duration`) and format via `StringLoader.getQuantityString()` (a locale-aware plural resolver); `title()`/`domain()` take the current `SearchMatch` plus flags and return `HtmlString` (HTML-marked text the view renders with highlights). `Track` input on `listenDurationEstimate()` comes from the TTS (text-to-speech: article audio) pipeline.
## Key pieces
- `timeEstimate()` / `timeToReadEstimate()` — full-article estimates with different plural strings ("5 min" vs "5 min read"). WHY: list rows and reader headers use different wording.
- `timeLeftEstimate(Item?)` — remaining-time estimate, scaling by `ItemUtil.getPercent()` (percent already scrolled). WHY: "X min left" shrinks as you read.
- `listenDurationEstimate(Track?)` — audio duration estimate for listen mode. WHY: same "N min" UI for spoken articles.
- `generateDisplayViewingTimeEstimate()` — shared core: rounds seconds to minutes (rounds up at :30), scales by unscrolled percent, formats the plural. WHY: one rounding rule everywhere.
- `title()` / `domain()` — pick server-highlighted vs locally-highlighted text based on `isSearching`/`useLocalHighlights`, falling back to plain title/host. WHY: search rows show match highlights, normal rows do not.
- `highlight()` — wraps the matched substring in highlight markup. WHY: single implementation behind both title and domain.
## Junior notes
- `@PluralsRes` + `StringLoader.getQuantityString()` handle languages with complex plurals — never concatenate `" min"` manually or translations break.
- `domain()` shows the host name rather than the raw URL highlight from the server: the server returns a highlighted full URL but the UI displays only the host, so the highlight must be re-applied — that mismatch is why the method looks more complex than `title()`.
