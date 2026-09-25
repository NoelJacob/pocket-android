# Pocket/src/main/java/com/pocket/app/package-info.java

## What this is
The package overview for `com.pocket.app`: the concrete Pocket app layer — Activities, fragments, and user-facing screens like the Reader, Saves list, and Settings.

## How it fits
Everything under this package is a real screen or UI component built on the SDK/data layers below it. New app screens belong here (in a feature subpackage), not in `sdk` or `repository` code.

## Key pieces
- Package Javadoc — WHY: the only content; it orients newcomers that this is the UI/app-experience layer and names its headline areas.

## Junior notes
- If you are adding a new screen, start by finding its feature subpackage here (e.g. `list`, `listen`, `premium`) and follow the fragment + ViewModel pattern its neighbors use.
