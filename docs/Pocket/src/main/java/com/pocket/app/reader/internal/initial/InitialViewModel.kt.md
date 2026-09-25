# Pocket/src/main/java/com/pocket/app/reader/internal/initial/InitialViewModel.kt
## What this is
Placeholder ViewModel for the reader's blank starting page. It holds no state, exposes no events, and performs no work — it exists only because the databound `InitialFragment` layout expects a ViewModel to bind to.
## How it fits
Hilt-provided (constructor params provided automatically — here there are none) to `InitialFragment`, which sets it as `binding.viewModel`. All real routing happens in the fragment's `handleNavigationEvent`; this class is along for the structural ride.
## Key pieces
- The empty `@Inject constructor()` — the only member; its presence keeps Hilt wiring and the layout binding compiling if the screen later gains state.
## Junior notes
- Don't add logic here just to fill it: routing decisions belong to `ReaderFragment`, and per-screen state belongs to the article/collection/original-web ViewModels this placeholder hands off to.
- `@HiltViewModel` on an empty class looks odd but is required for the `by viewModels()` delegate to receive it via Hilt.
