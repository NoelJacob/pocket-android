# Pocket/src/main/java/com/pocket/app/settings/appicon/AppIconSettingsFragment.kt
## What this is
This is the app-icon picker screen, written fully in Compose (Kotlin's declarative UI toolkit): an app bar plus a grid of launcher icons (Classic, Monochrome, Pride) with an Automatic/default section. Tapping an icon tells the ViewModel to switch; the selected icon shows a highlighted ring. A preview composable renders the same grid in Android Studio without running the app.
## How it fits
A Hilt entry-point fragment reached from PrefsFragment's app-icon row via the goToAppIconSettings navigation action. It grabs AppIconSettingsViewModel via viewModels(), renders viewModel.automaticIcon/allIcons as AppIconUiState rows through the content { } Compose bridge, and up navigates with findNavController().navigateUp(). Icon taps call onAutomaticIconClick/onIconClick(index).
## Key pieces
- `AppIconSettingsFragment`: thin host — sets Compose content in onCreateViewImpl, notifies onViewShown in onViewCreatedImpl.
- `AppIconUiState (drawable, label, selected)`: the render model decoupling Compose rows from PackageManager lookups.
- `AppIconSettingsScreen` (two overloads): ViewModel-bound entry vs pure stateless grid — WHY two: the stateless one is previewable and testable without Hilt.
- `AppIcon()` row + `header()` LazyGridScope helper: circular clipped icon, label, selected border, click handling; content-type enum keeps headers and icons recycled correctly.
## Junior notes
- rememberDrawablePainter bridges Android Drawable icons into Compose Image — needed because launcher icons come from PackageManager, not Compose resources.
- LazyVerticalGrid with GridCells (fixed count or adaptive) is the Compose grid; itemsIndexed preserves per-icon index so taps map back to AppIcons.all positions.
