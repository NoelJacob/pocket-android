# Pocket/src/main/java/com/pocket/app/home/loading/SlatesSkeletonView.kt
## What this is
This is the grey placeholder for the Home slate feed (the vertically scrolling recommendation rows) while the lineup loads. A `ThemedConstraintLayout2` that inflates a static skeleton layout — phone or tablet variant — with a fade-in when shown.
## How it fits
Home's layout shows this while `HomeViewModel` is in its loading state, then swaps to `SlatesAdapter`'s recycler. The tablet variant (`ViewHomeTabletSlatesSkeletonBinding`) mirrors the wide hero-plus-grid layout; the phone variant mirrors the hero-plus-horizontal-row layout.
## Key pieces
- `init` block — picks tablet vs phone binding via `FormFactor.isTablet(context)`, guarded by `!isInEditMode` so Android Studio's layout preview (which has no real context) falls back to the phone layout instead of crashing.
- `setVisibility()` override — same 1-second fade-in as the sibling skeletons.
## Junior notes
- `isInEditMode` is true only inside the IDE preview; the guard exists purely so the preview renders — don't remove it as dead code.
- Like the other skeletons, this view is dumb: visibility comes from the parent's databinding, never from inside.

