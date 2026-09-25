# Pocket/src/main/java/com/pocket/app/ItemCap.kt
## What this is
Sets the ceiling on how many unread items are kept in the local cache: 5500 for fresh installs, 8000 for existing installs that already grew larger. The value is decided once per install and persisted in the `item_cap` pref. It exists because unbounded local lists caused out-of-memory crashes for power users with tens of thousands of saves.
## How it fits
Hilt creates it at startup (`Versioning.isFirstRun` tells new installs from upgrades); the chosen `cap` is handed to `LegacyMigration` so migration itself respects the bound. The backend fetch/get endpoints sync at most 5000 items, but local tracking grows without bound as users keep saving, which is the gap this closes.
## Key pieces
- `cap`: the decided limit; WHY a plain val read by others instead of a function is that every consumer must agree on one stable number for the whole install.
- Set-once init block: writes the pref only when unset, choosing 5500 for first runs and 8000 for upgrades; WHY upgrades keep the higher cap is to avoid suddenly forgetting items a long-time user already has cached.
## Junior notes
- Never change the stored value after it is set: comments warn that cached item identities were derived with this number in mind, so changing it mid-install corrupts cache assumptions. A lower global cap requires migrating the app to server-side filtering/search first.
- `Versioning.isFirstRun` (no stored previous version) is the new-install signal; do not substitute a timestamp or install-source check.
