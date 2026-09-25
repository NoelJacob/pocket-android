# pocket-ui/src/main/java/com/pocket/ui/view/AppBar.java

## What this is
The classic View-system top bar: a left navigation icon, a title, a row of action buttons, and a thin bottom divider line. For the user it is the familiar screen header with a back arrow or X on the left and icons (share, overflow) on the right. It is built by inflating `view_app_bar.xml` into itself and exposes a fluent `bind()` API so each screen can set the title, swap the left icon, and add actions in code.

## How it fits
Screens declare it directly in XML layouts (e.g. `<com.pocket.ui.view.AppBar app:leftIcon="up" android:title="..."/>`, as in `pocket_ui_playground.xml`), with `leftIcon` (none/up/close from `attrs.xml`), `android:title`, and `bottomDivider` parsed in `init()`. Callers then configure it at runtime via `appBar.bind().title(...).onLeftIconClick(...).addIconAction(...)`, and `AppBarBindings.kt` adds a databinding (XML layouts bound to ViewModel fields) adapter so `app:title="@{viewModel.uiState.title}"` works (used in `fragment_home_details.xml`). It extends `ThemedConstraintLayout`, so background and colors follow the Pocket light/dark theme automatically. Note: `AppBar.kt` is a separate, newer Compose (the modern declarative UI toolkit) reimplementation with the same name in the same package — the two are not subclasses of each other; new Compose screens should use the `.kt` version, legacy XML screens use this one.

## Key pieces
- `Binder` (inner class, returned by `bind()`): the whole configuration API — WHY it exists is so screens chain calls like `.title().withCloseIcon().addIconAction()` instead of touching child views directly. `clear()` resets to up-arrow + empty actions so recycled headers start clean.
- `title(CharSequence)` / `title(@StringRes)`: set the header text; databinding calls through here.
- `withUpArrow()` / `withCloseIcon()` / `withNoLeftIcon()` / `leftIcon(drawable, contentDescription)`: swap the left navigation affordance; hiding it lets the title align to the left edge. `layout()` / `upRes()` / `closeRes()` are `protected` so subclasses can swap the inflated layout or icons.
- `addIconAction(drawable, contentDescription, listener)` / `addButtonAction(label, listener)`: append an `IconButton` or text `BoxButton` to the right-side `actions` row; `viewExists()` dedupes by view id so rotation (configuration change re-running setup) does not double-add buttons.
- `divider(show)`: toggles the bottom hairline; null-safe for subclasses whose layout omits it.
- `fixSmallIcon()`: pads icons narrower than 24dp (e.g. the thin overflow icon) so they stay tappable, then shrinks the actions container's right padding by the same amount so the last icon still aligns to the screen edge.
- `getLeftIcon()` / `getActionView(index)`: escape hatches for tests or callers that need the raw views.

## Junior notes
- `ThemedConstraintLayout` (a `ConstraintLayout` that re-applies colors on theme change) is the base class — this view never sets hardcoded colors; the `cl_pkt_bg` background and `Pkt_ThinDivider` style handle that.
- XML attributes come from `R.styleable.AppBar` in `attrs.xml`; `ta.recycle()` after reading is mandatory to return the shared attribute array to the pool.
- `leftIcon` is an `IconButton` created in the layout, but action icons are created in code with a `ContextThemeWrapper(R.style.Pkt_IconButton)` so they get the same style without XML.
