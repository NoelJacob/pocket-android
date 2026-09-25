# Pocket/src/main/java/com/pocket/app/App.java
## What this is
The Android `Application` subclass: the single object created when the process starts, before any screen. Marked `@HiltAndroidApp` (the entry point of Hilt DI, which then provides constructor params automatically across the app), it holds every app-scope component as an `@Inject` field and exposes them through the legacy `PocketApp` interface. `onCreate` initializes date/time support, runs one-time stale-data cleanup (`Forgetter`), applies piracy/debug guards, and routes long-presses on error snackbars to support email.
## How it fits
Hilt constructs all the `@Inject` fields (Pocket engine, UserManager, threads, prefs, navigation helpers). Screens reach components via injection or the legacy `app().xxx()` accessors. `AbsPocketActivity` calls `onActivityChange` on resume/pause, which fans out to `AppLifecycle` observers and logs app-open/app-close analytics. `getActionContext` builds the analytics context (connectivity, orientation, theme, session ids) attached to those events.
## Key pieces
- `@Inject` component fields + `PocketApp` overrides: WHY both exist is migration history; new code should inject the component directly instead of calling `app().threads()` etc.
- `onCreate`: process-level init (ThreeTen dates, `Forgetter.forget`, debuggable/package-name kill switches, `PktSnackbar.init` support-email hook). WHY the kill switches run first is to refuse to boot a tampered build before any user data loads.
- `onActivityChange`: swaps the current-activity reference and dispatches `onActivityResumed`/`onActivityPaused` to all `AppLifecycle` observers; WHY it also calls `setUserPresent` is so analytics sees one clean app-open/app-close pair per foreground session.
- `setUserPresent`: syncs `opened_app`/`closed_app` actions and fires `onUserPresent`/`onUserGone`; edge-triggered (`sIsUserPresent` check) so multiple activities resuming do not double-log.
- `viewUrl`: opens a URL in an external browser with a "no browser found" dialog fallback.
- `getActionContext`: builds per-event analytics context (online status, orientation, theme from the current activity, session/item-session ids).
## Junior notes
- Static accessors (`getApp()`, `getContext()`, `getActivityContext()`) are deprecated because they hide dependencies and leak screens; prefer Hilt constructor injection, or `App.from(context)` when a static is unavoidable.
- `isUserPresent()` returns whether any activity context exists, not whether the user is interacting; presence transitions are what `onUserPresent`/`onUserGone` observers (e.g. `AppOpen` clearing deep links) rely on.
- `onLowMemory` and `onConfigurationChanged` just forward to the dispatcher; component-specific handling belongs in an `AppLifecycle` implementation, not here.
