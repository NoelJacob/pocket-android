# Pocket/src/main/java/com/pocket/util/android/Clipboard.kt

## What this is
The app's clipboard manager: it reads URLs from the system clipboard (to offer "save this link?"), and writes text/URLs back to it (for copy/share actions). It solves two product problems: detecting a copied link exactly once — showing the save prompt without nagging on every app open — and suppressing that prompt for URLs the app itself just copied. For example, opening Pocket with a news link copied elsewhere triggers the save prompt, while copying a link inside the Reader does not re-prompt.

## How it fits
It is an app-scoped `@Singleton` injected via Hilt DI (constructor params provided automatically) with the application context, `AppMode`, an error reporter, preferences, and the app-lifecycle dispatcher. It is exposed through `App.clipboard()` / `PocketApp.clipboard()`. Readers include `AbsPocketActivity.checkClipboardForUrl()`, which polls `getUrl()` whenever a window gains focus (opted out of by `AddActivity`, `AuthenticationActivity`, and `PremiumMessageActivity`); writers include `ArticleFragment` and `ArticleActionModeCallback`, which call `setText()` for copy-link actions. It implements `AppLifecycle` so logins can reset its bookkeeping.

## Key pieces
- `getUrl()`: returns a clipboard URL once, then null for repeats. WHY it exists: powers the "save from clipboard?" prompt with built-in dedupe. It filters non-URL clips on Android S+ via `TextClassifier` confidence, extracts the first URL with `UrlFinder`, and compares its hash against a stored preference.
- `getText()`: raw plain-text read with OEM-crash protection. WHY it exists: some devices crash just reading the clipboard, so failures are reported (or rethrown on internal builds) instead of killing the app.
- `setText(text, name)` / `performSetText(text)`: writes text and optionally shows a "<name> copied" toast. WHY split: the toast is UI sugar around the raw clipboard write.
- `setUrl(url, name)`: writes a URL while pre-recording its hash. WHY it exists: stops the app from prompting to save a link it just placed there itself.
- `lastUrlHash` (a per-user `IntPreference`): the dedupe memory. WHY prefs and not RAM: the "already offered" state must survive process death.
- `onLoggedIn()`: consumes any pending clipboard URL on login. WHY it exists: prevents the save prompt from ambushing users right after sign-up/login.

## Junior notes
- `ClipboardManager` behavior differs by API level: the `TextClassifier.TYPE_URL` confidence gate only runs on Android S and above; below that, `UrlFinder` alone decides.
- Dedupe is by `String.hashCode()`, which can theoretically collide — acceptable for a best-effort prompt, but never use this pattern for security or identity decisions.
- `getText()` reads `primaryClip` inside try/catch because of real-world OEM crashes; follow that pattern (report, don't crash) when touching system clipboards.
