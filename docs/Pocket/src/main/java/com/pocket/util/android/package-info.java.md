# Pocket/src/main/java/com/pocket/util/android/package-info.java

## What this is
The package-level Javadoc for `com.pocket.util.android`: general-purpose, reusable helpers built on top of Android's SDK. It mirrors the parent `com.pocket.util` package's "clean enough to open-source one day" bar, scoped to Android-framework utilities. It contains no code — only the package description, plus a `@see` link back to the parent package.

## How it fits
It documents the package holding all the files beside it: intent, clipboard, email, form-factor, view, timeout, watcher, and animation helpers, along with the `drawable`, `fragment`, `text`, `thread`, `view`, and `webkit` sub-packages. New Android-specific helpers belong here; pure-Java/Kotlin helpers with no Android dependency belong in the parent `com.pocket.util`.

## Key pieces
- Package Javadoc comment with `@see com.pocket.util`: the only content. It draws the boundary between platform-agnostic utilities (parent) and Android-bound ones (this package).

## Junior notes
- `package-info.java` is a Java convention for package documentation; Javadoc renders it as the package overview page.
- The sorting rule of thumb: if the helper imports anything from `android.*` or `androidx.*`, it lives here; otherwise it goes one level up.
