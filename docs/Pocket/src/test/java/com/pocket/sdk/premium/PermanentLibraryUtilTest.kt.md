# Pocket/src/test/java/com/pocket/sdk/premium/PermanentLibraryUtilTest.kt
## What this is
Single golden-value test for `PermanentLibraryUtil.hash`, which hashes (timestamp, user id, item URL) into the permanent-library token. It proves known fake inputs produce one exact hex digest.
## How it fits
Guards production `com.pocket.sdk.premium.PermanentLibraryUtil`, used in premium permanent-library URLs. Pure JVM test; the URL-encoded input mirrors how the value is passed in production.
## Key pieces
- `hash()` — URL-encoded example URL plus fake timestamp/userId, asserts the full hex string; WHY: any algorithm/encoding change breaks the token, so the digest is pinned exactly.
## Junior notes
- Golden tests are brittle by design: do not fix by pasting the new hash — verify the algorithm change is intended first.
- URL-encoding matters; hashing the raw URL yields a different digest.
