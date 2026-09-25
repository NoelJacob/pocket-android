# Pocket/src/main/java/com/pocket/app/settings/SystemDarkTheme.java
## What this is
This is the "follow system theme" setting for Android Q and above, where the OS itself has a light/dark mode. When the user enables it, Pocket stops using its manual light/dark pick and instead mirrors the system's uiMode on every configuration change and login. It logs analytics (tealium Pv events) for on/off toggles so the team can measure adoption.
## How it fits
A singleton Feature (gated capability) + AppLifecycle observer created via Hilt DI. PrefsFragment shows its toggle only when isEnabled (company builds / supported OS) and routes toggle taps through turnOn/turnOff rather than writing the preference directly. It drives Theme (the actual light/dark store) by calling set(LIGHT/DARK) from updateTheme whenever the system configuration changes.
## Key pieces
- `turnedOnByUser` (BooleanPreference): the persisted switch; isOn requires both the feature flag and this pref — WHY disabling the feature remotely reverts to manual theme.
- `turnOn(view/context)` / `turnOff(view/context)`: apply the matching system theme immediately AND record the analytics event with the triggering view/context.
- `onConfigurationChanged(configuration)` / `onLoggedIn(...)`: re-evaluate the system uiMode and push it into Theme; the two isSystemSetToDark/Light helpers read Configuration.uiMode.
- `onLogoutStarted()` (LogoutPolicy): decides whether the preference survives logout.
- `Analytics` inner class: fires the follow-system on/off events.
## Junior notes
- Feature/Audience gating means isEnabled can be false on old OS versions or non-company builds — PrefsFragment hides the row entirely in that case, so do not assume the pref exists.
- onConfigurationChanged here is an app-lifecycle callback, not an Activity callback — it fires for system theme flips even when no settings screen is open.
