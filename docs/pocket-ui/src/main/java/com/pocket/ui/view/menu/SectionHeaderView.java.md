# pocket-ui/src/main/java/com/pocket/ui/view/menu/SectionHeaderView.java

## What this is
A non-tappable divider header that labels a section of a menu or settings screen: a small title with an optional action button on the right, a thin bottom divider by default, and an optional thick top divider. Both dividers and the button can be toggled from XML or code.

## How it fits
Inflated from `R.layout.view_section_header` and dropped between groups of rows in menus and settings screens; `ThemedPopupMenu` also uses it for section titles (with the bottom divider turned off). Hosts configure it from XML with `app:showDividerTop`, `app:showDividerBottom`, and `android:text`, or in code via `bind()` — setting the label, showing/hiding dividers, and attaching the optional button.

## Key pieces
- `SectionHeaderView` — extends `ThemedConstraintLayout` (theme-aware layout); `init()` inflates the layout, applies `bind().clear()` defaults, then overlays any XML attributes.
- `bind()` / `Binder` — fluent configuration API; `clear()` resets to label-empty, top divider off, bottom divider on, no button, no all-caps.
- `Binder.label(...)` — sets the header text from a literal or string resource.
- `Binder.showTopDivider` / `showBottomDivider` — toggle the thick top / thin bottom divider views (VISIBLE vs GONE).
- `Binder.button(text, onClick)` — shows a small action button (hidden when text is null via `setTextOrHide`) and wires its tap handler.
- `Binder.textAllCaps` — uppercases the label rendering (visual only, not the underlying string).

## Junior notes
- Custom attributes live in `R.styleable.SectionHeaderView` — `showDividerTop`/`showDividerBottom` plus the stock `android:text`, which is why the header text can be set directly in layout XML.
- `setTextOrHide` is a Pocket helper that sets text or collapses the view to GONE when null — so passing a null button label cleanly removes the button.
