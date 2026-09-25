# Pocket/src/main/java/com/pocket/sdk/http/NetworkStatus.java

## What this is
A tiny interface that answers "what kind of internet do we have right now?" for the whole app: online or not, wifi or not, metered or not, plus a stability check and change listeners. It exists so features depend on this abstraction instead of Android's `ConnectivityManager` directly, which keeps them testable and lets the implementation change per API level.

## How it fits
Implemented by `AndroidNetworkStatus`; consumed by `AppSync` (when to push queued actions), `HttpClientDelegate` (exposed via `status()`), and any feature gating downloads on wifi/unmetered. Producers of connectivity info implement the queries; consumers register a `Listener` and re-query on change. In the save-to-network trace, the sync layer consults `isOnline()` before attempting the push and retries when `onStatusChanged` fires.

## Key pieces
- `isOnline()` — whether there is a usable internet connection (validated where the device supports it). Exists as the first gate before any network attempt.
- `isStable(duration)` — whether the connection has held for the given time. Exists for operations that should not start on a flapping connection.
- `isWifi()` / `isUnmetered()` — transport and cost signals. Exist so bulk downloads (offline cache, article assets) can wait for cheap connectivity.
- `Listener.onStatusChanged(status)` — the single change callback; receivers re-query what they care about. Exists so sync reacts to connectivity instead of polling.

## Junior notes
- "Online" means validated internet on capable devices but may degrade to "joined to a network" on others; never assume reachability of a specific host from it.
- The listener may fire on a background thread; do not touch views directly from it.
