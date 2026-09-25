# Pocket/src/main/java/com/pocket/app/ActivityMonitor.java
## What this is
A Hilt singleton (Hilt DI means constructor params are provided automatically; `@Singleton` means one instance for the whole app) that remembers the latest Activity (one Android screen) in each lifecycle state. Each `AbsPocketActivity` reports in via `onActivityCreate`, which attaches a listener tracking it through restart/start/resume/pause/stop/destroy. Other components ask it "what screen is the user on?" instead of holding their own Activity references.
## How it fits
Created once by Hilt and fed by every Pocket activity. `UserManager.logout()` uses `getVisible()` to find a screen for the logout progress dialog; `AddActivity` uses it to detect a multi-window Pocket task; `App.getActionContext()` uses `getAvailableContext()` for analytics theming. `App.onActivityChange` is the parallel static path that fires analytics and dispatcher events.
## Key pieces
- `State`: enum of tracked slots (CREATED through STOPPED); WHY a map keyed by state is that only the most recent activity per state matters, so one slot per state is enough.
- `onActivityCreate`: registers a per-activity lifecycle listener; WHY the listener lives here centrally is so no activity subclass has to remember to report each transition.
- `set`: removes all old references to the activity, records the new state, and notifies listeners (started/resumed/paused only).
- `getVisible`: resumed, else started, else paused-only-if-in-multi-window; null when nothing is on screen. WHY the multi-window carve-out is that a paused activity can still be visible side-by-side.
- `getAvailableContext`: most-recent-first fallback chain down to STOPPED; for "best Activity to use as a Context", not "what the user sees".
- `Listener` / `SimpleListener`: start/resume/pause callbacks plus a no-op base so observers override only what they need.
## Junior notes
- Only one Activity is remembered per state; a second started activity evicts the first from that slot, so never treat this as a full back-stack.
- Listeners fire only for STARTED/RESUMED/PAUSED (destroy/removal is silent), and `addListener` has no unregister-on-destroy logic, so pair every add with a remove.
