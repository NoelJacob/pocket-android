# pocket-ui/src/main/java/com/pocket/ui/view/notification/PktSnackbar.java

## What this is
Pocket's custom bottom notification bar: a rounded banner with an icon, title, message, and optional right-aligned action button (e.g. "Undo"). Types cover teal info banners and apricot error banners, each dismissable (swipe or ×) or persistent, plus "outside" variants for surfaces outside the main app like the share overlay. Only one bar shows at a time — showing a new one dismisses the current one.

## How it fits
Feature screens call `PktSnackbar.make(activity, type, anchor, message, dismissListener, actionText, actionId, actionListener)` then `.show()`; `make` attaches the bar to the activity's content view (or the anchor's parent) positioned above the anchor, and records it in the static `currentBar` so the next `make`/`dismissCurrent` clears it first. Internally the banner is a `CoordinatorLayout` inflated from `R.layout.view_pkt_snackbar`; text/icon/action are set through `bind()`, and removal from the view hierarchy happens in an internal dismiss wrapper. `ErrorReporter` (installed once via `init`) handles taps/long-presses on error banners for bug reporting.

## Key pieces
- `Type` — ERROR_DISMISSABLE, ERROR_EXCLAIM, DEFAULT_DISMISSABLE, DEFAULT (in-app teal/apricot styles) plus DEFAULT_OUTSIDE / ERROR_EXCLAIM_OUTSIDE (neutral background for external surfaces).
- `make(...)` overloads — convenience entry points funneling into the full `make(activity, type, anchor, message, listener, actionText, actionId, actionListener)`; always dismisses the current bar first (single-queue behavior).
- `getCurrent` / `dismissCurrent` — inspect or programmatically clear the showing bar; the static ref is a `WeakReference` so a dead Activity can still be garbage-collected.
- `getNotificationLayoutParams` / `setAnchor` — position the bar above the anchor with side margins, handling FrameLayout, ConstraintLayout, RelativeLayout, and CoordinatorLayout parents; margins become bar padding so swipe-dismiss is not clipped at the edges.
- `userDismissable(bool)` — attaches (or removes) a `PktSwipeDismissBehavior` on the inner root so the user can swipe the bar away; swipe reports `DismissReason.USER`.
- `displayMessage` / `dismissMessage` — 500ms fade in/out; dismiss is guarded by `isDismissed` so it fires once, then notifies `onDismissListener` with USER vs PROGRAMMATIC.
- `Binder.type(type)` — applies background, text/action colors, swipe behavior, and icon per type (× button for dismissable, error icon + report hooks for errors).
- `Binder.onAction(text, id, listener)` — shows the action button only when both text and listener are present; tapping it dismisses the bar, then calls the host listener.
- `Binder.title` / `message` / `singleLineMessage` / `error` — banner text setters (empty text hides the line) plus the `Throwable` attached to error reports.
- `Binder.show()` / `dismiss()` — fade in / programmatic fade out.

## Junior notes
- Coroutines (background tasks) are not used here — show/dismiss are plain view animations (`ViewPropertyAnimator`); calling `dismiss()` twice is safe because of the `isDismissed` guard.
- `make` adds the view with GONE visibility — nothing appears until you call `show()`; forgetting `show()` is the most common "my snackbar never appeared" bug.
- Long-pressing an error banner triggers the global `ErrorReporter` — pass the real `Throwable` via `Binder.error(...)` or reports arrive with no stack trace.
