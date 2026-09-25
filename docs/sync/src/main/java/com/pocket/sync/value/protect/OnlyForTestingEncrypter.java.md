# sync/src/main/java/com/pocket/sync/value/protect/OnlyForTestingEncrypter.java

## What this is

A deliberately insecure StringEncrypter that applies a trivial reversible change for tests: it exercises the encrypt/decrypt plumbing (dangerous-value redaction paths, protected-field round trips) without needing real keys or the Tink dependency. The name and javadoc both shout that it must never ship in production.

## How it fits

Engine and parser tests plug this in wherever a StringEncrypter is required but secrecy is irrelevant. Production Android uses TinkEncrypter instead.

## Key pieces

- `encrypt/decrypt` — trivially reversible transforms proving the plumbing, not the cryptography

## Junior notes

- Grep-able name on purpose: any occurrence outside test sources is a ship-blocking defect, no exceptions.
