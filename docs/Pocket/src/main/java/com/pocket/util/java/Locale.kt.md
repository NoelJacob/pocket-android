# Pocket/src/main/java/com/pocket/util/java/Locale.kt
## What this is
An injectable (Hilt DI, meaning constructor params provided automatically) wrapper that exposes device-locale answers to the rest of the app. It currently answers "is the device German" and renders the locale as a `language-country` string.
For example, a ViewModel injects `Locale` and calls `locale.isGerman()` instead of digging through Android resources itself.

## How it fits
Backed by the static `LocaleUtils` checks, reading the app-context resources configuration. Any Hilt-managed class (ViewModels, repositories) can inject it as a singleton; it replaces direct static `LocaleUtils` calls in new code so locale becomes a mockable dependency.

## Key pieces
- `Locale @Inject constructor(@ApplicationContext context)`: singleton holding the app context. WHY it exists: gives Hilt a seam to provide and fake locale info.
- `isGerman()`: delegates to `LocaleUtils.isGerman(context)`. WHY it exists: the one locale branch the app currently needs as an injectable query.
- `toString()`: `"<language>-<country>"` from the active configuration locale. WHY it exists: a compact locale tag for logging or debugging.

## Junior notes
- Hilt DI means you never call `Locale(...)` yourself; you declare it as a constructor param and Hilt supplies the singleton.
- `@ApplicationContext` provides the long-lived app context, which is safe to hold; never store an Activity context in a singleton like this or you leak the screen.
