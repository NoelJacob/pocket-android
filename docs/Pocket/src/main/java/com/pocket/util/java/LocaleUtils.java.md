# Pocket/src/main/java/com/pocket/util/java/LocaleUtils.java
## What this is
Static checks over the Android device locale: whether Pocket is translated for it, whether it is English/US/German, and tolerant locale comparison. It shields callers from two- vs three-letter ISO codes and from `MissingResourceException` on odd devices.
For example, `LocaleUtils.isGerman(context)` gates German-specific behavior, and `localeEquals(a, b, ...)` compares locales field-by-field.

## How it fits
Called wherever behavior branches on language or region, and wrapped for injection by the `Locale` Hilt singleton (`Locale.isGerman()` delegates here). `isLocalizedForPocket()` defines the set of languages Pocket ships translations for; callers pass an Activity context or null to fall back to the app context.

## Key pieces
- `isLocalizedForPocket(Context)`: true when the current language is one Pocket translates. WHY it exists: single source of truth for "do we support this language".
- `isEnglish` / `isUS` / `isGerman` (Context and Locale overloads): case-insensitive language/country checks. WHY they exist: tiny readable predicates replacing string compares scattered across features.
- `localeEquals(l1, l2, language, country, variant)`: selective comparison tolerant of 2- vs 3-letter codes. WHY it exists: `Locale` codes vary by platform, so naive `equals()` yields false negatives.
- `getCountrySafely` / `getLanguageSafely`: ISO3 lookups with `MissingResourceException` fallback. WHY they exist: some devices throw on ISO3 codes; these degrade gracefully.

## Junior notes
- `context.getResources().getConfiguration().locale` is the legacy locale API; on newer Android versions code should migrate to `getLocales().get(0)`.
- `StringUtils.equalsIgnoreCase` (Apache Commons) is null-safe, so these checks never throw on a null language or country.
