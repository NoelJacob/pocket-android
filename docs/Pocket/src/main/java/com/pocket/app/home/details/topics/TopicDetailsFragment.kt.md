# Pocket/src/main/java/com/pocket/app/home/details/topics/TopicDetailsFragment.kt
## What this is
This is the details screen for one topic (e.g. "Technology"): a titled list of that topic's stories with loading and error states. Like its slate sibling, it is a thin subclass of `DetailsFragment` — all rendering and event handling is inherited.
## How it fits
Home's topic row navigates here with a `topicId` string via Safe Args (`TopicDetailsFragmentArgs`). `onViewCreated` hands it to `TopicDetailsViewModel.onInitialized(topicId)`, which fetches the feed over the network. Story taps return as `GoToReader` events and navigate to the reader with `InitialQueueType.Empty` (single article, no queue).
## Key pieces
- `viewModel by viewModels()` — Hilt-provided `TopicDetailsViewModel` scoped to this fragment.
- `onCreateViewImpl()` — inflates the shared `FragmentHomeDetailsBinding` and binds the ViewModel so the XML can observe `uiState` directly.
- `goToReader(event)` — navigates via `TopicDetailsFragmentDirections.topicDetailsToReader(...)` with `navigateSafely` to swallow double-tap navigation crashes.
## Junior notes
- Screen tracking string is `"topicDetails"` — keep it stable for analytics continuity.
- Unlike slate details (positional index into cached data), this screen is ID-addressed and network-backed, so it can show the loading skeleton and the retry snackbar.

