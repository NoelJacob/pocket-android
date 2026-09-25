# Pocket/src/main/java/com/pocket/sdk/util/AbsPocketActivity.java
## What this is
The base Activity (one full app screen) every Pocket screen extends. It wires up analytics tracking, app-lifecycle events, theming (light/dark/sepia), snackbar/error display, and fragment navigation. Subclasses just provide their content; this class handles the shared chrome.
## How it fits
Created by the Android system when a screen opens (e.g. MainActivity, Reader, Settings). It inflates a PocketActivityRootView (content plus persistent Listen audio bar and rotation-lock views), delegates fragment swaps to PocketFragmentManager, and calls downstream into Session, AppLifecycle, Theme, and ReviewPrompt.
## Key pieces
- `AbsPocketActivity` itself: lifecycle hub — onCreate/onResume wiring, theme application, back-press routing, snackbar helpers.
- `from(Context)`: walks up Context wrappers to find the owning activity; used by PermissionRequester and fragments to get the host.
- `OnLifeCycleChangedListener` / `SimpleOnLifeCycleChangedListener`: callback interface for permission results, activity results, and lifecycle events without subclassing.
- `showPage(...)` / fragment helpers: push AbsPocketFragment screens (handles phone vs tablet dialog presentation via FormFactor).
- `isUserPresent()`: opt-out hook so background-only activities skip analytics/lifecycle tracking.
## Junior notes
- Android Activity = one screen with lifecycle callbacks (onCreate once, onResume each time it becomes visible). Always call through to super and register listeners in onCreate — PermissionRequester depends on this ordering.
- Theming here uses a custom Transition (ThemeChange); theme switches animate background/text colors rather than recreating the screen.
