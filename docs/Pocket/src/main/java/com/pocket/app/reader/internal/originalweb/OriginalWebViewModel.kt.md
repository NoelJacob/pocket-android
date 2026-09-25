# Pocket/src/main/java/com/pocket/app/reader/internal/originalweb/OriginalWebViewModel.kt
## What this is
Placeholder ViewModel for the original-web reader screen. It holds no state because all real work (launching the Custom Tab, theming, previous/next) lives in `OriginalWebFragment` itself.
## How it fits
Hilt-provided to `OriginalWebFragment`, which binds it as `binding.viewModel` so the `FragmentOriginalWebBinding` layout has something to reference. Navigation and browser logic bypass it entirely.
## Key pieces
- The empty `@Inject constructor()` — the whole class; keeps Hilt and databinding compiling and gives future state a home.
## Junior notes
- Don't mistake this for the menu overlay's logic — the overlay sheet has its own `OriginalWebBottomSheetViewModel`; this one stays empty by design.
- Removing it would require editing the layout's `<data>` block too — leave the pair as-is.
