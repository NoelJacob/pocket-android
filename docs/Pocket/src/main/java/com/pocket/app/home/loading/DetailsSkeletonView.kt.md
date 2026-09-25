# Pocket/src/main/java/com/pocket/app/home/loading/DetailsSkeletonView.kt
## What this is
This is the grey placeholder ("skeleton") layout shown on the details screens while the story list loads. It is a custom view (a `ThemedLinearLayout`, i.e. a theme-aware `LinearLayout` from the Pocket UI library) that inflates a static shimmer-like placeholder layout — no data, no logic.
## How it fits
The details-screen XML includes this view alongside the real `RecyclerView`; the ViewModel's `ScreenState.loadingVisible` boolean toggles which one is visible via databinding. On tablets it inflates `ViewHomeTabletDetailsSkeletonBinding`, on phones `ViewHomeDetailsSkeletonBinding`, matching the real list's 1- vs 2-column shape so the swap doesn't jump.
## Key pieces
- Constructor `(context, attrs)` — the standard custom-view signature so it can be placed in XML layouts.
- `setVisibility()` override — plays a 1-second alpha fade-in whenever the skeleton becomes VISIBLE, softening the transition from empty screen to grey boxes.
## Junior notes
- `FormFactor.isTablet(context)` is checked at inflation time only; a config change recreates the view anyway, so no need to handle it dynamically.
- This view never hides itself — visibility is driven entirely by the parent layout's binding; don't add timers or listeners here.

