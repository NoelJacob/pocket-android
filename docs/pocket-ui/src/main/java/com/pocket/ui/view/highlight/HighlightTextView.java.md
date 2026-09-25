# pocket-ui/src/main/java/com/pocket/ui/view/highlight/HighlightTextView.java

## What this is
A text view that always renders its content as an amber highlighted quote (like a pulled article excerpt). Whatever text is set, the user sees it with the padded highlight background behind every line; empty text stays blank. It deliberately has no disabled appearance: disabling the view is ignored.

## How it fits
Used for quote/excerpt displays (for example highlighted passages) where the highlight styling must be automatic. It extends ThemedTextView (Pocket's theme-aware TextView) with the `Pkt_Text_Small_Medium` appearance and amber color `pkt_themed_amber_4`. A TextWatcher (before/on/after-text-changed callbacks) reinstalls exactly one full-text HighlightSpan plus its HighlightedRegion after every text change, delegating the actual rectangle drawing to HighlightSpan.

## Key pieces
- `init()` TextWatcher: the whole behavior; `afterTextChanged()` checks for exactly one HighlightSpan and one HighlightedRegion covering the full range, and rebuilds via `HighlightSpan.removeAll()` + `new HighlightSpan(this, color).attach(s, 0, s.length())` when anything differs.
- Self-healing guard: the span-coverage check prevents infinite loops (re-adding spans triggers the watcher again, but the second pass sees correct coverage and stops).
- `color` (amber-4 state list): resolved once via NestedColorStateList (theme-aware color resolver) and handed to each new span.
- `setEnabled(true)` override: forces enabled regardless of input; WHY it exists is the design has no disabled quote style, so callers cannot accidentally grey it.

## Junior notes
- Do not add your own HighlightSpans to this view's text: the watcher treats any deviation (extra spans, partial coverage) as corruption and wipes plus rebuilds them.
- The guard compares span boundaries to `length() - 1`/`0`; text set in pieces (append calls) triggers a rebuild per change, which is correct but wasteful in a loop, so prefer a single `setText()`.
- `setEnabled(false)` silently does nothing; if a screen needs a disabled quote state, that requires a change here, not at the call site.
