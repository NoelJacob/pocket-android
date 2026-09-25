# Pocket/src/main/java/com/pocket/app/home/details/slates/SlateDetailsFragment.kt
## What this is
This is the details screen for one Home slate (a curated row like "Today's top stories"): a titled list of that slate's stories. It is a thin subclass — all list rendering, app bar, and overflow handling live in `DetailsFragment`. This file only connects the navigation argument (which slate was tapped) to its ViewModel and defines where a tap goes.
## How it fits
The Home screen navigates here with a slate `index` (position in the lineup) via Safe Args (`SlateDetailsFragmentArgs`, the Navigation Component's generated typed-argument class). `onViewCreated` passes that index to `SlateDetailsViewModel.onInitialized()`. Story taps come back as `GoToReader` events and navigate to the reader with an empty queue (`InitialQueueType.Empty`), meaning the reader opens just that article with no up-next list.
## Key pieces
- `viewModel by viewModels()` — Hilt-provided `SlateDetailsViewModel`, scoped to this fragment.
- `onCreateViewImpl()` — inflates `FragmentHomeDetailsBinding`, sets `lifecycleOwner` (required so databound `StateFlow`s update the UI) and binds the ViewModel for the XML layout.
- `goToReader(event)` — navigates via `SlateDetailsFragmentDirections.slateDetailsToReader(...)` using `navigateSafely` (a helper that ignores duplicate rapid taps that would otherwise crash navigation).
## Junior notes
- Screen tracking uses the string `"slateDetails"` from `getScreenIdentifierString()` — analytics only, but keep it stable.
- The slate is looked up by positional `index`, not an ID; if the lineup refreshes and reorders while this screen is open, the content follows the new slate at that position.

