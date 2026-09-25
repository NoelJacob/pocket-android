# utils/src/main/java/com/pocket/util/java/DomainUtils.java

## What this is

This is a shared utility used across modules: a small, dependency-free helper that one feature needed and others reuse. It owns no app state and starts no work on its own.

## How it fits

Any module depending on `utils` (pure JVM) or `utils-android` (needs Android APIs) imports it directly. It is a leaf in the dependency graph: it must never depend back on feature code.

## Key pieces

- `DomainUtils` (class, line 5) — core type of this file; callers reference it by name.
- `getHost` (fun, line 10) — Will take a url such as http://www.stackoverflow.com and return www.stackoverflow.com
- `getBaseDomain` (fun, line 33) — Based on : http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/2.3.3_r1/android/webkit/CookieManager.java#CookieManager.getBase
- `cleanHostFromUrl` (fun, line 54) — Reduces {@code url} down to the host part, removing slashes, and `www.` subdomain.

Concrete endpoints referenced here:

- `http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/2.3.3_r1/android/webkit/CookieManager.java#CookieManager.getBaseDomain%28java.lang.String%29`
- `http://www.stackoverflow.com`

## Junior notes

- Leaf helper with no app state: keep it dependency-free and never let it import feature code, or every module pays for the cycle.
