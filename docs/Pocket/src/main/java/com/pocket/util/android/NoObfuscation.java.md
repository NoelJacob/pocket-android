# Pocket/src/main/java/com/pocket/util/android/NoObfuscation.java

## What this is
A marker interface (an interface with no methods) that tells the minifier to leave implementing classes alone. It solves the problem that ProGuard/R8 (the tools that shrink and rename code for release builds) would otherwise rename classes and members that must keep their names — for example bridges reached by name from outside the obfuscated code. The keep rules live in the file's own Javadoc: keep the interface, keep all implementers, keep their methods.

## How it fits
Any class that must survive obfuscation implements it. Known implementers are `ArticleFragment`, the `InitialQueueType` navigation enum (whose values cross the navigation-graph boundary), and `JsInterface` (the WebView JavaScript bridge, reached by name from JavaScript in the Reader). No code calls into this interface; the build tooling reads the `-keep` rules instead.

## Key pieces
- `NoObfuscation` (empty interface): the tag. WHY empty: it carries no behavior — its presence is purely a signal to the keep rules.

## Junior notes
- R8/ProGuard shrinking and obfuscation run on release builds: names you see in debug builds may be renamed in release unless kept. Only tag classes that truly need stable names; over-tagging bloats the APK.
- The canonical danger cases are reflection, JavaScript bridges (`addJavascriptInterface` methods are looked up by name), navigation enums, and anything serialized by name.
- If you add a new implementer, double-check the `proguard.cfg` rules quoted in the Javadoc are still active — the interface alone does nothing without them.
