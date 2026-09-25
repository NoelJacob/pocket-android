# Pocket/src/main/java/com/pocket/app/reader/internal/collection/loading/CollectionSkeletonView.kt
## What this is
The shimmer-style loading placeholder shown while a Collection fetches: grey boxes standing in for the header and story cards. It picks a phone or tablet skeleton layout at inflate time and fades itself in so the swap from placeholder to real content feels less abrupt.
## How it fits
Placed in the Collection screen layout alongside the real content; `CollectionViewModel.UiState.screenState` toggles its visibility (`Loading` shows it, `Default` hides it). `ThemedConstraintLayout2` is the themed base view that follows the app's light/dark theme.
## Key pieces
- `init` block — inflates `ViewCollectionSkeletonTabletBinding` on tablets (checked via `FormFactor.isTablet`) or `ViewCollectionSkeletonBinding` on phones; the `isInEditMode` guard keeps Android Studio's layout preview from running device checks.
- `setVisibility(...)` — when becoming `VISIBLE`, runs a 1-second alpha fade from 0 to 1 before delegating to super; hiding is instant.
## Junior notes
- Fading only on show (not on hide) is deliberate: the skeleton dissolves into content rather than flashing out.
- This view has no data or logic — if the loading look needs to change, edit the skeleton layout XML, not this class.
