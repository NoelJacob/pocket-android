# sync-pocket-android/src/test/java/com/pocket/sdk/AbsPocketTest.java

## What this is

Shared base class for Pocket SDK tests: builds a Pocket instance against scripted fakes and offers testAccount plus common setup so concrete test classes (PocketTest and friends) start from the same logged-in-ish state. Test bases like this keep fixture construction in one place instead of copy-pasted across every test.

## How it fits

Extended by Pocket SDK tests needing a working Pocket without network; subclasses inherit the fixture and focus on their behavior. It sits between raw JUnit and the PocketSpec/V3 transport tests.

## Key pieces

- `testAccount` — the shared account fixture giving tests a consistent identity to sync against

## Junior notes

- Put only truly-shared fixtures here: dumping every helper into the base makes tests depend on setup they do not use.
