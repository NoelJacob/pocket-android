# Pocket/src/main/java/com/pocket/util/java/BytesUtil.java
## What this is
Static helpers for converting between bytes and KB/MB/GB. It also estimates an average saved-item size and formats a byte count into a short display string like "250 MB" or "1.2 GB".
For example, `BytesUtil.mbToBytes(500)` turns a 500 MB cache-limit slider value into bytes, and `bytesToMb(bytes)` goes the other way.

## How it fits
Used by the offline-cache settings path: `CacheLimitSeekbar` converts slider progress with `mbToBytes`, `CacheLimitPreferenceView` formats the limit and estimates how many items fit via `getAverageBytesPerItem()`, and `CacheSettingsEvents` logs the limit with `bytesToMb`. `ImageCache` also caps downloads with `mbToBytes(4)` for `MAX_FILE_SIZE`. `getAverageBytesPerItem()` reads the `DOWNLOAD_TEXT` pref to pick 333 KB (article-only) vs 444 KB (auto).

## Key pieces
- `KB`, `MB`, `GB`: byte multipliers (1024-based doubles). WHY they exist: one shared definition so conversions never drift between call sites.
- `kbToBytes` / `mbToBytes` / `gbToBytes`: float units to `long` bytes. WHY: turns human settings into storage math.
- `bytesToKb` / `bytesToMb` / `bytesToGb`: bytes to `double` units. WHY: turns measured sizes back into display units.
- `getAverageBytesPerItem()`: rough per-item estimate driven by the download-text pref. WHY: lets the cache screen say "room for ~N items".
- `bytesToCleanString(context, bytes)`: formats bytes as whole MB below 1000 MB else one-decimal GB with localized unit strings. WHY: single place for the cache-size label.

## Junior notes
- `float`/`double` math then cast to `long` truncates fractions; that is fine for estimates but never use it for exact billing-style accounting.
- `getAverageBytesPerItem()` reaches into `App.getApp().prefs()`, so it only works after the app object exists; pure-looking util with a hidden app dependency.
