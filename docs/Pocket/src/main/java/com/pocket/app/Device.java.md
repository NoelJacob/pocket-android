# Pocket/src/main/java/com/pocket/app/Device.java
## What this is
Provides device identity (manufacturer, model, product, Android ID, serial) sent to Pocket endpoints, with per-field overrides editable on internal builds for testing server behavior against fake devices. Production builds always return the real values; team/eng builds return the override when one is set. Values come from `Build` constants and `Settings.Secure`, read once at construction.
## How it fits
Hilt-injected into `PocketSingleton` (builds the API device identity, spoofed on internal builds) and `UserManager` (internal-build login extras carry the spoofed Android/serial IDs). The beta settings UI writes the overrides via the `setOverride*` setters. `AppMode` decides whether overrides are enabled at all.
## Key pieces
- Dual constructors: the `@Inject` one reads the live Android environment; the manual one takes explicit defaults; WHY two is testability, letting tests supply fake hardware values without device shadows.
- `manufacturer()` / `model()` / `product()` / `anid()` / `sid()`: each returns override-if-enabled-and-set else the real default.
- `overridesEnabled`: true only when `AppMode.isForInternalCompanyOnly()`; WHY the gate lives in the constructor is so production can never be tricked into spoofing at runtime.
- `dcfig_device` pref group: the five override backing prefs; `setOverride*` setters no-op unless overrides are enabled.
## Junior notes
- `Safe.get(...)` wraps the resolver/serial reads so a throwing provider yields null instead of crashing startup; expect `anid()`/`sid()` to be nullable.
- On Android O+ `Build.SERIAL` requires privileged permission, hence the pre-Oreo guard returning null on newer devices; do not "fix" that by adding a permission.
