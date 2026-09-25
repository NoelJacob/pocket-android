# Pocket/src/main/java/com/pocket/util/android/webkit/JsInterface.java
## What this is
A safer base for `addJavascriptInterface` bridges (Java objects exposed to page JavaScript). It tracks install state and can detach the bridge with `setEnabled(false)`, using reflection on pre-API-11 devices where no removal API existed.
For example, an article page exposes `ArticleJsInterface` while trusted content shows, then disables it before navigating elsewhere.

## How it fits
Subclassed by per-screen bridges attached via `WebView.addJavascriptInterface()` (e.g. the article screen's JS interface in `ArticleFragment`). It implements `NoObfuscation` so ProGuard/R8 keeps bridge method names JavaScript relies on. Bridges must only be enabled on fully trusted content.

## Key pieces
- `JsInterface(webView, interfaceName)`: binds the bridge to its JS name. WHY it exists: names the object page scripts will call.
- `setEnabled(enabled)`: adds or removes the interface (`removeCompat()` on old APIs). WHY it exists: minimizes the window when page JS can reach native code.
- `removeCompat()`: reflection-based removal for legacy Android. WHY it exists: old platforms lacked `removeJavascriptInterface`, so detachment needs a fallback.
- `isEnabled()`: install state. WHY it exists: lets hosts assert the bridge is up or down before loading content.

## Junior notes
- Every public method on a bridge is reachable from any script on the loaded page; validate all parameters and assume hostile input, especially on untrusted URLs.
- On Android 2.2-4.1 a platform exploit lets page JS reach Java methods beyond the bridge; never install a bridge on pages or scripts you do not fully control.
- The class file footer contains a sample HTML probe for auditing which interfaces are installed and exploitable; use it when adding bridge methods.
