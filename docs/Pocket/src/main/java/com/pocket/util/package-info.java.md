# Pocket/src/main/java/com/pocket/util/package-info.java

## What this is
The package-level Javadoc for `com.pocket.util`: general-purpose, reusable helper classes with no Pocket-specific logic. It states the aspiration that these helpers are clean enough to be extracted as an open-source library one day, while admitting they were written for this app first. It contains no code — only the package description.

## How it fits
It documents the parent package of everything under `util/`, including the Kotlin extensions (`AnyExtensions`, `ListExtensions`, `FlowExtensions`), the loaders (`StringLoader`, `DrawableLoader`), `DisplayUtil`, `BackPressedUtil`, and the `android` and `java` sub-packages. New generic helpers belong here; Android-framework helpers belong in the `android` sub-package (see its own `package-info.java`).

## Key pieces
- Package Javadoc comment: the only content. It sets the bar for what belongs in this package (reusable, extendable, app-agnostic) versus feature code elsewhere.

## Junior notes
- `package-info.java` is a Java convention: a file that holds only a package statement plus its documentation comment. Javadoc tooling renders it as the package overview page.
- When deciding where a new helper goes, re-read this bar: if it mentions Pocket concepts, it does not belong in `com.pocket.util`.
