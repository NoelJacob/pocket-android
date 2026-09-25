# Pocket/src/main/java/com/pocket/app/auth/Authentication.kt
## What this is
The event namespace for the login screen: a `sealed class` (a closed set of subtypes the compiler checks exhaustively) of one-shot commands the login ViewModel emits and the login Fragment renders. It carries no logic or state, just the shared vocabulary (authenticate, go to default screen, go back, error/deleted-account toasts, intent-filter toggle, team tools) both sides agree on.
## How it fits
`AuthenticationViewModel` emits these into its `events` SharedFlow (an observable stream for fire-once commands); `AuthenticationFragment.setupEventListener` collects and switches on them to open the browser login, finish to the main app, toast errors, or toggle the auth-callback receiver.
## Key pieces
- `Event.Authenticate`: start the Custom-Tab login flow; WHY separate from the button handler is the VM also triggers it automatically when `skipOnboarding` is set.
- `Event.GoToDefaultScreen` / `GoBack`: post-login forward vs backed-out-of-login retreat.
- `Event.ShowErrorToast` / `ShowDeletedAccountToast`: failure vs returning-after-deletion notice.
- `Event.DisableCredentialsCallbackIntentFilter`: turn off the redirect catcher once credentials are consumed so stray `pocket://auth` links stop intercepting.
- `Event.OpenTeamTools`: internal-build long-press escape hatch.
## Junior notes
- Sealed-class `when` branches must stay exhaustive: adding an event here forces a compile error in the fragment until it is handled, which is the point, so do not add a catch-all `else`.
- These are navigation-grade commands, not UI state: they belong in the events flow, never in `UiState`, or rotation would re-fire them.
