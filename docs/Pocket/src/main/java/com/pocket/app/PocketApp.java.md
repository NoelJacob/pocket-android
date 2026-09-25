# Pocket/src/main/java/com/pocket/app/PocketApp.java
## What this is
The legacy "everything accessor" interface listing every app-scope component (`pocket()`, `user()`, `threads()`, `prefs()`, `dispatcher()`, and thirty more), implemented by `App`. It predates Hilt and is explicitly `@Deprecated`: new code must receive components via Hilt constructor injection instead of calling `app().xxx()`. It stays because transitional call sites (notably `AddActivity`) still use it.
## How it fits
`App` implements it; legacy helpers accept a `PocketApp` (e.g. `AddItemFromIntentUtil.add(intentItem, app, ...)`, `Help.concatSettings`) so activities, fragments, and static utilities can reach components without full injection. Hilt modules (`PocketModule`) are the replacement path for new code.
## Key pieces
- The ~40 accessor methods: each returns one singleton component; WHY the interface is fat is historical (one global bag), which is exactly what Hilt's granular injection fixes.
- `@Deprecated` on the interface plus on `pktcache()`/`prefs()`: signals "do not extend"; the inner deprecations mark superseded storage layers.
- `mode()`, `session()`, `dispatcher()`, `appOpen()`, `listManager()`, `saveExtension()`: the accessors most reached-for by the files in this chunk.
## Junior notes
- Never add a method to this interface: inject the component where you need it with Hilt (`@Inject` constructor param or field) and let this file shrink over time.
- `PocketApp` parameters in helper signatures (like the add-from-intent util) are a migration seam: prefer passing the specific component (e.g. `Pocket`) when you touch those helpers.
