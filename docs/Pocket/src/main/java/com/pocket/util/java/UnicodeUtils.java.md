# Pocket/src/main/java/com/pocket/util/java/UnicodeUtils.java
## What this is
Byte-level encoding conversion that sniffs a byte-order mark (BOM, the magic prefix bytes naming a Unicode encoding) before decoding. `convert(bytes, encout)` detects the input encoding, skips the BOM, and re-encodes into the requested output encoding.
For example, `UnicodeUtils.convert(raw, "UTF-8")` normalizes a downloaded article body that might be UTF-8, UTF-16LE/BE, or plain ASCII into UTF-8.

## How it fits
A legacy leaf helper for normalizing downloaded or cached text when the declared charset cannot be trusted. It works around the old Sun bug 4508058 where the JDK mishandled BOM-prefixed streams, so modern code should prefer explicit charsets/`InputStreamReader` unless it truly needs BOM sniffing.

## Key pieces
- `convert(bytes, encout)`: detect-then-transcode pipeline. WHY it exists: one call that handles "unknown Unicode flavor in, desired encoding out".
- `getBOM(enc)`: maps an encoding name to its BOM bytes. WHY it exists: lets convert read and optionally re-emit the marker.
- `UnicodeInputStream`: pushback stream that reads up to 4 leading bytes to detect UTF-32/UTF-16/UTF-8 BOMs, exposing `getEncoding()` and `getBOMOffset()`. WHY it exists: the detector that tells convert how to decode and where content starts.

## Junior notes
- A BOM is metadata, not content; writing it into the middle of a stream or double-emitting it corrupts text, which is why the offset handling matters.
- `convert` throws generic `Exception`; callers must catch broadly and decide on a fallback encoding.
- This predates reliable `StandardCharsets` usage; for known encodings, prefer those over this helper.
