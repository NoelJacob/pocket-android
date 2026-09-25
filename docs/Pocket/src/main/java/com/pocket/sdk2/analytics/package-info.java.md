# Pocket/src/main/java/com/pocket/sdk2/analytics/package-info.java
## What this is
One-paragraph package overview stating that Pocket analytics currently means Action Context: extra metadata attached to every sync `Action` describing its circumstances. It points readers to the `context` subpackage for the actual tools. Prose documentation only.
## How it fits
Parent doc for `sdk2/analytics/` and its `context` subpackage. Anyone adding a new analytics event starts here and is routed to `Interaction` / `Contextual` docs.
## Key pieces
- Package declaration itself: the only executable content; everything else is Javadoc prose describing Action Context.
## Junior notes
- `package-info.java` files contain no classes or logic, only package-level Javadoc. Nothing here runs at runtime.
