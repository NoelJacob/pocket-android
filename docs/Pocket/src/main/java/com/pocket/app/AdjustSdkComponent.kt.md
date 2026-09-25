# Pocket/src/main/java/com/pocket/app/AdjustSdkComponent.kt
## What this is
This source file no longer exists in the repo: it was listed in the handoff chunk but there is no `AdjustSdkComponent.kt` under `Pocket/src/main/java/com/pocket/app/`. It previously held app-scope wiring for the Adjust SDK (third-party install-attribution analytics). There is nothing to call or construct here anymore.
## How it fits
Nothing references this file now. If attribution work resurfaces, its replacement would be created by Hilt at app start (like other app-scope components) and would hook activity lifecycle callbacks from `App` / `AbsPocketActivity`, downstream of nothing and upstream of analytics reporting.
## Key pieces
- No symbols remain; the old doc mentioned `registerAdjustSdkLifecycleCallbacks` and activity created/started/resumed/paused hooks, all removed with the file.
## Junior notes
- If you find imports or docs still mentioning `AdjustSdkComponent`, they are stale: delete the reference rather than recreating the file.
- Hilt DI (constructor params provided automatically) is how its replacement should be scoped if attribution is ever re-added.
