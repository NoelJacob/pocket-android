# Pocket/src/main/java/com/pocket/app/settings/OpenSourceLicensesFragment.kt
## What this is
This is the open-source licenses screen: a Compose (Kotlin's declarative UI toolkit) page showing every library Pocket uses, rendered by the AboutLibraries LibrariesContainer. It has a simple app bar with an up button and a full-screen auto-generated license list. There is no ViewModel or data loading — the library scans the bundled license metadata at display time.
## How it fits
Reached from PrefsFragment's "Open Source" row via the goToOpenSourceLicenses navigation action. It extends AbsPocketFragment (Pocket's fragment base) but overrides only onCreateViewImpl with a Compose `content` block; up navigates via findNavController().navigateUp().
## Key pieces
- `OpenSourceLicensesFragment.onCreateViewImpl`: builds the whole screen — PocketTheme + Column(AppBar, LibrariesContainer) — WHY no XML layout exists for this screen.
- `LibrariesContainer(showVersion = false)`: third-party composable that lists detected libraries and their licenses.
## Junior notes
- `content { }` (fragment.compose) lets a fragment return a Compose UI instead of inflating XML — the Compose tree is disposed with the view, so no manual cleanup.
- PocketTheme must wrap Pocket composables for correct light/dark colors; AppBar here is Pocket's own composable, not the platform action bar.
