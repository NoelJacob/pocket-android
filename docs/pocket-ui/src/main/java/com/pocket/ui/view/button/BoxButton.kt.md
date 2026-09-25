# pocket-ui/src/main/java/com/pocket/ui/view/button/BoxButton.kt

## What this is
The Jetpack Compose (the newer declarative UI toolkit, where UI is Kotlin functions instead of XML layouts) version of the box button. For the user it is the same idea as the old `BoxButton.java`: a filled rounded button with centered text for a primary action. It is a thin wrapper around Material3's `Button` (Google's standard component library) with Pocket's small shape, horizontal padding, and typography applied.

## How it fits
Called directly from Compose screens that need a primary action — pass `text` and `onClick`, optionally a `Modifier` (Compose's equivalent of layout params / view settings chained onto a component). It styles the label with `PocketTheme.typography.h7` in `onTeal` (light text meant to sit on teal) inside `PocketTheme` shapes. It shares only a name with `BoxButton.java`; there is no inheritance or delegation between them — old XML screens use the `.java` one, new Compose screens use this one.

## Key pieces
- `BoxButton(text, onClick, modifier)` — WHY it exists: one call site for "Pocket-styled primary button" in Compose so screens don't repeat shape/padding/typography. `text` is the label, `onClick` is a lambda (an inline callback function) run on tap.
- `BoxButtonPreview` — a `@Preview` (an Android Studio annotation that renders the composable in the IDE without running the app) showing the button with placeholder text; exists only for design-time checking.

## Junior notes
- `MaterialTheme.shapes.small` controls the corner rounding here — to change the button's corners app-wide you change the theme shape, not this file.
- `modifier = Modifier` default means callers that don't care about sizing pass nothing; callers that need width/padding chain it (e.g. `Modifier.fillMaxWidth()`).
