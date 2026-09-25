# Pocket/src/main/java/com/pocket/app/PocketModule.kt
## What this is
The Hilt dependency-injection module that teaches the app how to build its singletons (Hilt DI means constructor params are provided automatically from these definitions). `@Module` + `@InstallIn(SingletonComponent::class)` scopes everything here to the whole app process lifetime. It covers prefs, app mode, legacy migration, the Pocket engine, network status, push, clock, resource loaders, and repository bindings.
## How it fits
Hilt consults this at startup whenever a `@Inject` constructor asks for one of these types: activities/fragments/viewmodels get `Preferences`, `Pocket`, `ItemRepository`, and friends without manual construction. `providePocket` delegates to `PocketSingleton`; `provideLegacyMigration` runs only when old storage exists; `PocketInterfaces` maps repository interfaces to their sync-engine implementations.
## Key pieces
- `providePrefs`: two `SharedPreferences` files (default + `pocketAppPrefs`) merged into one `Preferences`; WHY two files is legacy storage separation preserved so existing installs keep their settings.
- `provideAppMode`: derives PRODUCTION/TEAM_ALPHA/DEV from `AppVersion`; downstream gates (`Feature`, `Device` overrides) all flow from this one mapping.
- `provideLegacyMigration`: builds the v2-storage migrator only if needed (null otherwise), threading item-cap, threads, and dispatcher through; WHY nullable is that most launches have nothing to migrate.
- `providePocket`: returns `pocketSingleton.instance`; WHY indirection instead of building here is that engine construction (pools, logging, migrations) is `PocketSingleton`'s whole job.
- `providePocketPush` / `provideNetworkStatus` / `provideClock`: push wiring, connectivity, and a clock with UTC fallback if the device zone breaks; the fallback reports the error rather than crashing.
- `PocketInterfaces` (`@Binds` trio): `SyncEngineItemRepository` -> `ItemRepository`, and the user/notes equivalents; `@Binds` (interface-to-impl alias) is cheaper than `@Provides` because Hilt constructs the impl itself.
## Junior notes
- `@Provides` (you write the builder) vs `@Binds` (you declare the alias): use Binds for interface/impl pairs, Provides when construction needs logic.
- Everything here is `@Singleton`: it lives for the process lifetime and is NOT cleared at logout, so never store per-user state in these objects; per-user state belongs in `forUser` prefs or logout-managed components.
