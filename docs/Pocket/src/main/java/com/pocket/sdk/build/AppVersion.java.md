# Pocket/src/main/java/com/pocket/sdk/build/AppVersion.java

## What this is
Answers "what build is this?" for the whole app: version name/code from the Android package manager, which app store it came from, which API keys to use, and which run mode (dev, team-alpha, production) applies. Most values derive from `BuildConfig` fields baked in at build time, plus a runtime guess for the store. Screens like settings and support emails read the version/store strings from here.

## How it fits
Singleton injected wherever build info is needed (`App`, settings, logging, `PocketServer` flavor decisions). The constructor maps `BuildConfig` flags (`DEBUG`, `I_B`) to an `AppMode`; `getConsumerKey()` picks the phone vs tablet API key (a `FormFactor` check, i.e. screen-size helper) used for server calls. `TeamTools` reads `getVersionName()` for its internal dialog; analytics/server code reads the store name and mode.

## Key pieces
- `mode()` — returns the `AppMode` derived at construction (dev, team-alpha, production). Exists as the single switch the app uses to gate internal behavior.
- `getConsumerKey()` / `getApiId()` — return the API key for this form factor and the numeric id derived from its first four characters. Exist because phone and tablet builds use different server keys.
- `getConfigName()` — returns the baked-in market key (e.g. `"play"`). Exists to distinguish store-specific builds.
- `getStoreName(useHardCoded, ...)` / `findStoreName(...)` — returns either the build-time store name or a runtime guess based on which store apps are installed. Exists for server reporting when the build key is generic.
- `getVersionName(...)` / `getVersionCode(...)` — read the package manager's version label and number. Exist for user-facing display and diagnostics.

## Junior notes
- The no-argument `getVersionName()`, `getVersionCode()`, `getStoreName()`, and `getPackageInfo()` overloads are deprecated; use the `Context` variants.
- `findStoreName` returns the first match in a fixed priority order, not a definitive answer; treat the guessed name as approximate.
