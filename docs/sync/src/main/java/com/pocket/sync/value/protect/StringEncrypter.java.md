# sync/src/main/java/com/pocket/sync/value/protect/StringEncrypter.java

## What this is

The one-method seam for encrypting sensitive strings: encrypt/decrypt around whatever value the engine must store or transmit protected. OnlyForTestingEncrypter fakes it in tests; TinkEncrypter (Google's Tink library) implements it for real on Android. Code that handles dangerous values programs against this interface so key management never leaks into parsing or storage logic.

## How it fits

Protected fields flow through this at persist/serialize boundaries; isAvailable on the Tink side lets the app degrade gracefully where device crypto is missing.

## Key pieces

- `encrypt/decrypt contract` — the single seam separating value handling from key management and crypto choice

## Junior notes

- Encryption here covers stored strings, not transport security: HTTPS and server-side controls are separate, complementary layers.
