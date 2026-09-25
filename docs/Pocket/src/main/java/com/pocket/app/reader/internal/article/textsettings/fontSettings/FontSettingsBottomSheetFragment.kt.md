# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/fontSettings/FontSettingsBottomSheetFragment.kt
## What this is
The font-picker bottom sheet itself: a list of typefaces the user can switch the article to. It shows the list via `FontSettingsAdapter`, calls `viewModel.onInitialized()` when opened, and handles the two navigation outcomes (back to display settings, or to the Premium paywall).
## How it fits
Opened from `TextSettingsBottomSheetFragment` when the user taps the font row (that sheet dismisses itself first). Hilt DI (constructor params provided automatically) supplies `Premium` for the paywall path. `ReturnToTextSettings` reopens a fresh `TextSettingsBottomSheetFragment` and dismisses this one; `GoToPremium` calls `premium.showPremiumForUserState` and dismisses.
## Key pieces
- `setupRecyclerView()` — installs `FontSettingsAdapter` with the view lifecycle owner and ViewModel; the adapter self-subscribes to the font list.
- `setupEventListener()` — collects `FontSettings.Event` with `collectWhenResumed` (collects the flow only while resumed) and performs the sheet swap or paywall navigation.
- `newInstance()` — trivial factory so callers never touch arguments (this sheet takes none).
## Junior notes
- Sheet-to-sheet navigation here is "show new, dismiss old" via `parentFragmentManager` — the back stack is not used, so the back chevron is an explicit `ReturnToTextSettings` event, not a pop.
- Databinding (`binding.viewModel = viewModel`) plus the adapter means this fragment has almost no UI code — new behavior goes in the ViewModel, navigation goes in the event listener.
