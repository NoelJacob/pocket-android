# Pocket/src/main/java/com/pocket/app/auth/AuthenticationViewModel.kt
## What this is
The login screen's state machine (a Hilt ViewModel: UI-state owner surviving rotation, with an assisted factory for the per-fragment `skipOnboarding` flag). It exposes `uiState` (a StateFlow, an observable state stream: Default/Loading/Offline screen variants) and one-shot `events` (a SharedFlow of `Authentication.Event` commands). It handles offline gating, credential exchange via `UserManager`, the signed-out path, and the Firefox-migration notice flag.
## How it fits
`AuthenticationFragment` binds buttons to `onAuthenticateClicked` / `onContinueSignedOutClicked` and renders `uiState`; events drive the fragment's browser launch and navigation. Login itself delegates to `UserManager.authenticate` with a `loginWithAccessToken` op; success enables the signed-out experience (so future logouts land on Main) and emits `GoToDefaultScreen`; failure restores Default state with an error toast event. Network status comes from `HttpClientDelegate`.
## Key pieces
- `Factory.create(skipOnboarding)`: assisted injection; WHY assisted is the flag comes from fragment args, which Hilt cannot provide on its own.
- `onEventCollectionStarted`: first-collection logic: skip-onboarding auto-`Authenticate`, back-out-of-auth `GoBack`, plus the consume-once deleted-account toast; `initialEventCollectionStarted` makes it run once per VM.
- `onCredentialsReceived(authUri)`: Loading state, rewrites `pocket://` to `http://` for parsing, extracts `access_token` (plus `fxa_migration` into `FxaFeature`), sleeps 1s to dodge a token-propagation 401 race, then authenticates; failure path resets state and toasts.
- `checkForInternet` / `hideOfflineView` + `networkStatusListener`: offline shows the offline view and subscribes; the listener auto-hides on reconnect; `onOfflineCloseButtonClicked`/`onFragmentDestroyed` unsubscribe.
- `onAuthenticateLongClicked`: internal-builds-only long-press to `OpenTeamTools`; returns true (consumed) only then.
- `UiState.ScreenState` (Loading/Offline/Default with visibility flags): WHY flags on sealed objects is the XML binds visibility directly to them.
## Junior notes
- `_events` uses `extraBufferCapacity = 1` so `tryEmit` from click handlers never suspends; still prefer `tryEmit` (non-suspending) over `emit` outside coroutines to avoid dropped events on a full buffer.
- The 1-second sleep runs on `UserManager`'s background auth thread, not the UI thread; do not move credential exchange onto the main thread or that sleep would freeze the login spinner.
- `onFragmentDestroyed` MUST stay paired with the listener registration: leaking the `NetworkStatus.Listener` keeps the whole fragment reachable after navigation.
