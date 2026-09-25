# Pocket/src/main/java/com/pocket/app/reader/internal/article/textsettings/SettingsViewBindings.kt
## What this is
Glue between the text-settings bottom sheet layout and its ViewModel. Each function is a `@BindingAdapter` (databinding = XML layouts bound directly to ViewModel fields, so the XML attribute auto-calls this function when the value changes) that forwards one ViewModel flag to the `DisplaySettingsView` custom view.
## How it fits
The `FragmentTextSettingsBottomSheetBinding` layout binds `viewModel.uiState` fields (e.g. `fontSizeUpEnabled`) to XML attributes like `app:fontSizeUpEnabled`; these adapters push those values into `DisplaySettingsView.bind()` methods that enable/disable the +/- steppers, show or hide the Premium rows, and set the current font name.
## Key pieces
- `setPremiumUpsellVisible` / `setPremiumSettingsVisible` — toggle the upgrade upsell row vs. the premium-only controls.
- `setFontSizeUpEnabled`, `setFontSizeDownEnabled`, `setLineHeightUpEnabled`, `setLineHeightDownEnabled`, `setMarginUpEnabled`, `setMarginDownEnabled` — grey out each stepper arrow when its setting is at its min/max.
- `setFontChangeText` — passes a string-resource id for the current font's display name.
## Junior notes
- BindingAdapters must be top-level functions with the exact view type as the first param — that's how databinding finds them by attribute name.
- Adapters only push values one way here; taps flow back through explicit click listeners wired in `TextSettingsBottomSheetFragment`, not through two-way binding.
