# Pocket/src/main/java/com/pocket/app/auth/AuthenticationFragment.kt
## What this is
The login screen UI: a three-page onboarding pager plus sign-in / continue-signed-out buttons, bound to `AuthenticationViewModel` via databinding (XML layouts bound directly to ViewModel fields). It renders VM state (default/loading/offline) and converts VM one-shot events into actions: opening the browser login, finishing to the app, toasting, toggling the auth-redirect receiver, and opening team tools.
## How it fits
Hosted by `AuthenticationActivity`. Button taps call VM methods (`onAuthenticateClicked`, `onContinueSignedOutClicked`); the VM's `Authentication.Event`s drive `authenticate()` (Custom Tab on the server `/login` with consumer key + `pocket://auth` redirect), `GoToDefaultScreen` (`startDefaultActivity()` + finish), and receiver toggles. The `pocket://auth` redirect returns through the activity's `onNewIntent` into `onNewIntent()` here, then to `viewModel.onCredentialsReceived`.
## Key pieces
- `viewModel` with assisted `skipOnboarding` arg: fragment arguments flow into VM construction via the creation callback; WHY assisted (not plain inject) is each fragment instance carries its own flag.
- `setupEventListener` (collected `RESUMED`-scoped): the event switch; `onSubscription` notifies the VM collection started so it can emit its first-run events (skip-onboarding auto-auth, deleted-account toast).
- `authenticate()`: builds the login URL from `pocketServer.api()` (host-swapped to drop the `api.` prefix) with `redirect_uri=pocket://auth`, `force_logout=1`, consumer key; launched in a `CustomTabsIntent` (in-app browser sheet).
- `setCredentialsReceiverIntentFilterEnabled`: enables/disables the `AuthCallbackReceiverActivity` component so ONLY this screen intercepts the auth redirect; disabled after success or signed-out continue.
- `setupIntroPager`: three static `InfoPage`s (art + title + text) in an `InfoPageAdapter` with header logo; `trackScreenImpression` is currently an empty stub.
- `onNewIntent`: filters `pocket://auth...` data URIs into credentials handling.
## Junior notes
- Binding uses `viewLifecycleOwner` and is nulled in `onDestroyView`: never touch `binding` from coroutines that outlive the view (use `viewLifecycleOwner`-scoped collectors, as `setupEventListener` does).
- The PackageManager component toggle is process-global: if auth is interrupted (crash/back-out), the receiver can stay enabled; the VM's `DisableCredentialsCallbackIntentFilter` event exists to always switch it back off.
