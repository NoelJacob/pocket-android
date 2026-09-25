# pocket-ui/src/main/java/com/pocket/ui/view/button/PocketIconButton.kt

## What this is
The Compose (declarative Kotlin UI toolkit) equivalent of `IconButton`: for the user a 50dp tappable icon with Pocket-grey tinting. It wraps Material3's `IconButton` (Google's standard icon-button component) and fixes two Pocket conventions — a 50dp touch target matching the old View-system icons, and a default content tint of `PocketTheme.colors.grey3` — so individual screens don't repeat sizing and color setup.

## How it fits
Used by Compose screens and previews (e.g. the Compose `AppBar` preview hosts one with an `UpIcon` inside). The icon art itself comes from the `content` slot (a composable lambda — a UI-building function passed as a parameter, here typically `UpIcon()` or `OverflowMenuIcon()` from `PocketIcons.kt`). It provides the tint via `CompositionLocalProvider` (Compose's mechanism for passing values like colors implicitly down the UI tree) around `LocalContentColor` (the default color icons/text inherit when they don't specify one).

## Key pieces
- `PocketIconButton(onClick, modifier, content)` — WHY it exists: one place enforcing Pocket's icon-button size and tint. `onClick` is the tap callback lambda; `content` is the icon composable to display.
- `UpIconButtonPreview` — `@Preview` rendering the button with an up arrow in Android Studio without running the app; design-time checking only.

## Junior notes
- The `TODO` notes Material3 `IconButton` doesn't show the content description on long-press (unlike the View-system `IconButton`, which wires a tooltip) — TalkBack screen-reader users still hear it, but sighted users get no long-press label yet.
- To change the icon color for one usage, wrap the `content` icon with its own tint or pass a `modifier` — don't edit the shared `grey3` default or every Compose icon button shifts.
