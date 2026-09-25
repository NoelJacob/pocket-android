# pocket-ui/src/main/java/com/pocket/ui/view/chip/PocketChip.kt

## What this is
A small rounded pill (chip) with an optional icon, text label, notification badge dot, and close (x) affordance. The user sees a tappable lozenge: tapping selects or deselects it (swapping the icon between selected and unselected drawables when both are supplied), and the badge or close icon can be shown independently for state like "new" or "removable". Non-selectable chips ignore selection changes and act as static labels or buttons.

## How it fits
Used for tag/topic chips, filter pills, and removable selections across lists and editors. It extends ThemedLinearLayout (a theme-aware LinearLayout) and inflates `ViewChipBinding` (`view_chip` layout: icon + text + badge + close) into itself. Hosts configure it from XML attributes or from databinding layouts via the `chipSelected`, `badgeVisible`, `closeVisible`, `chipText`, and `enabled` adapters; selection changes flow through the standard `setSelected()` so existing selected-state drawables and listeners keep working.

## Key pieces
- `binding` (ViewChipBinding): the inflated icon/text/badge/close views; construction sets the pill background (`bg_chip`), horizontal orientation, and fixed dp padding here because the layout root is a `<merge>` tag with no root params of its own.
- `isSelectable`: when false, `setSelected()` is ignored so the chip renders statically; WHY it exists is read-only display chips sharing the same widget.
- `selectedImage` / `unselectedImage` / `hasImage`: drawable resources from `chipIcon` / `chipIconUnselected`; when no icon is given the icon view is hidden and the icon-text spacer collapses.
- `badgeVisible` / `closeVisible`: toggles the badge-dot and close-icon visibility (GONE when hidden so layout collapses).
- `setSelected()`: applies selection only when selectable, then swaps the icon via `setImage()`; calling super keeps the standard selected drawable state working.
- `setImage()` / `getImage()`: picks the unselected drawable when deselected and one exists, else the selected drawable.
- BindingAdapters (`chipSelected`, `badgeVisible`, `closeVisible`, `chipText`, `enabled`): XML-facing setters so layouts drive the chip from ViewModel fields; `chipText` also re-shows the icon spacer when an icon exists, and `enabled` propagates to all inner views.
- Custom attributes: `chipText`, `chipIcon`, `chipIconUnselected`, `chipSelectable` (see `PocketChip` styleable in attrs.xml).

## Junior notes
- This is selected-state, not checked-state: it uses `setSelected()`, so style it with `state_selected` selectors, not `state_checked`.
- `@JvmStatic` on each adapter is required: databinding looks for a Java static method, and without it the Kotlin companion method is invisible to the layout processor.
- `isSelectable=false` silently drops `setSelected()` calls (including from databinding), which can look like a broken binding; check that flag first when selection stops working.
