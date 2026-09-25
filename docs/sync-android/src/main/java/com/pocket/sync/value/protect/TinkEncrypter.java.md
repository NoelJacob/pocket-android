# sync-android/src/main/java/com/pocket/sync/value/protect/TinkEncrypter.java

## What this is

The production StringEncrypter, powered by Google's Tink cryptography library: real authenticated encryption for sensitive strings the engine must persist, following Tink's Android sample app patterns. encrypt/decrypt satisfy the engine seam while Tink owns keys and algorithms; isAvailable reports whether device crypto is usable so the app can degrade gracefully. Tests use OnlyForTestingEncrypter precisely to avoid needing this dependency.

## How it fits

Wired wherever the Android app persists protected fields; the engine only ever sees the StringEncrypter interface. Key management stays inside Tink and the app's keyset handling, never in engine code.

## Key pieces

- `encrypt/decrypt` — authenticated string encryption backed by Tink primitives
- `isAvailable` — device-capability check gating crypto use versus graceful fallback

## Junior notes

- Encryption protects stored data, not the transport: it complements HTTPS, and key loss means data loss, so keyset backup policy matters.
