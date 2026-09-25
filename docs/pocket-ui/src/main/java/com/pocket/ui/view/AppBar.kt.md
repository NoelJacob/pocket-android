# pocket-ui/src/main/java/com/pocket/ui/view/AppBar.kt

## What this is
The Compose (the modern declarative UI toolkit, where UI is functions rather than XML) rewrite of the Pocket top bar: a navigation icon slot, an expanding title slot, an actions slot, and a `ThinDivider()` hairline underneath. For the user it looks identical to the legacy header — back arrow on the left, title, action icons on the right, thin line below. It has no builder object; callers just pass composable lambdas for each slot.

## How it fits
New Compose screens call `AppBar(navigationIcon = {...}, title = {...}, actions = {...})` directly instead of inflating XML — see `AppBarPreview`, which shows a `PocketIconButton` with an `UpIcon` plus a `"Title"` text. It is deliberately parallel to `AppBar.java` (same package, same concept, same slot order) but shares no code with it: the `.java` version is the View-system widget inflated from `view_app_bar.xml` with a `Binder` API, while this file is the Compose function used by Compose screens. Spacing comes from `PocketTheme.dimensions.sideGrid` and `pkt_space_md`, and the title slot is wrapped in `TitleContainer` so it always uses the `h7` header text style. `ThinDivider()` comes from `Dividers.kt`.

## Key pieces
- `AppBar(modifier, navigationIcon, title, actions)`: the whole component — WHY the three lambda slots exist is so each screen supplies its own icon/title/buttons while the row layout, spacing math, and divider stay consistent.
- `TitleContainer` (private, `RowScope` extension): WHY it exists is to force the title text style (`PocketTheme.typography.h7` via `CompositionLocalProvider`) and give the title `weight(1f)` so it fills leftover space and pushes `actions` to the right edge. `navIconBuiltInSpace` (13dp) compensates for the icon button's internal padding so the visual alignment matches the design grid.
- `AppBarPreview`: a `@Preview` (an annotation that renders the composable in Android Studio without running the app) showing the bar at 480dp wide with an up icon and title.

## Junior notes
- Compose has no XML attributes here — there is no `leftIcon="up"` enum; the caller decides which icon composable to pass (or passes nothing for no icon).
- `RowScope` on the `actions` and `TitleContainer` parameters means those lambdas can use row-specific layout features like `weight()` — that is also why the title expands while icons stay fixed width.
