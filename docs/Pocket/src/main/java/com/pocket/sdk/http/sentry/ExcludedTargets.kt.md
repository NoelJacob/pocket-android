# Pocket/src/main/java/com/pocket/sdk/http/sentry/ExcludedTargets.kt

## What this is
A small builder that produces one regular expression matching "every URL except these excluded targets". It exists because the author finds raw regex hard to maintain: callers declare exclusions with a readable API and `toRegex()` compiles them into a single negative-lookahead pattern used to filter Sentry (crash reporting) network instrumentation.

## How it fits
Used wherever Sentry's HTTP tracing is configured: the app declares which hosts/paths should not be traced (to cut noise or avoid capturing sensitive endpoints), and the resulting regex is handed to the Sentry options as the exclusion filter. It has no app-flow callers beyond that configuration; it only builds the string.

## Key pieces
- `exclude(target, mode)` — adds one exclusion, either an exact host/path or a prefix. Exists so callers declare intent instead of hand-writing lookahead syntax.
- `toRegex()` — joins all exclusions into `^(?!(a|b)).*`, escaping dots and appending `$` for exact matches. Exists as the single compilation point producing the filter Sentry consumes.
- `Exclude` / `Mode.Exact` / `Mode.Prefix` — the internal record plus the two match kinds. Exist to keep the matching semantics explicit at each call site.

## Junior notes
- Dots are escaped but other regex metacharacters are not; keep targets to plain host/path text or the pattern can misbehave.
- An empty exclusion list still yields a valid regex; verify the generated string with a quick unit check when adding unusual targets.
