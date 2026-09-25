# Pocket/src/main/java/com/pocket/sdk2/analytics/context/package-info.java
## What this is
Package documentation for the Action Context system: the "when, why and where" metadata attached to every user action. It explains the two workflows — generating context with `Interaction.on()` at the action site, and defining context by implementing `Contextual` or binding views through `ContextualRoot`. It is prose documentation, not executable code.
## How it fits
Applies to everything under `sdk2/analytics/context/` (`Contextual`, `ContextualRoot`, `ContextualRootBinder`, `Interaction`). Feature code reads this to learn the pattern; base classes `AbsPocketActivity` / `AbsPocketFragment` are the canonical `ContextualRoot` implementations it references.
## Key pieces
- Package declaration itself: the only executable content; everything else is Javadoc prose describing the two Action Context workflows (`Interaction.on()` at the action site, `Contextual` / `ContextualRoot` for definitions).
## Junior notes
- `package-info.java` files contain no classes or logic, only package-level Javadoc. Nothing here runs at runtime.
