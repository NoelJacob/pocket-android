# Pocket/src/main/java/com/pocket/sdk/http/AndroidNetworkStatus.java

## What this is
Tracks whether the device actually has a working internet connection, and whether it is on wifi or an unmetered network, using Android's `ConnectivityManager` callbacks. It keeps cached flags (`isOnline`, `isWifi`, `isUnmetered`) plus listener notification, so the rest of the app can ask about connectivity synchronously instead of querying the system each time. Sync scheduling and download policies read it to decide when network work is allowed.

## How it fits
Constructed once with a `Context` and injected wherever the `NetworkStatus` interface is needed (`AppSync`'s `Sender`, `HttpClientDelegate`). At construction it snapshots all current networks, then registers a `NetworkCallback` (validated-internet capability) plus a default-network callback on newer Android versions to track the active network. State changes fan out to registered `Listener`s, e.g. to flush queued sync actions when connectivity returns.

## Key pieces
- `AndroidNetworkStatus(Context)` — snapshots current networks and registers the callbacks. Exists so the object starts correct and then stays correct via events.
- `online(Network)` / `lost(Network)` / `updateCapabilities(...)` — maintain the sets of usable and wifi networks. Exists to turn raw system callbacks into countable state.
- `updateStatus()` — recomputes the three public flags and notifies listeners only when something actually changed. Exists to avoid flapping callers with duplicate events.
- `isOnline()` / `isStable(millis)` / `isWifi()` / `isUnmetered()` — the query surface: current reachability, reachability held for a duration (via `lastDisconnect`), and transport cost. Exist so features can gate behavior (e.g. large downloads only on unmetered).
- `addListener()` / `removeListener()` — observer registration for connectivity changes. Exists so sync can react instead of polling.

## Junior notes
- "Online" requires the validated-internet capability, not just being joined to a network; captive portals without real internet read as offline.
- Listener callbacks can arrive on a background thread; hop to the needed thread before touching UI. All methods are synchronized, so keep listener work short to avoid blocking the callback path.
