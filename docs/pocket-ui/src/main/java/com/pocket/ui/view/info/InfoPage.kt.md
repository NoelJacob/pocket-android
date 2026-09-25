# pocket-ui/src/main/java/com/pocket/ui/view/info/InfoPage.kt
## What this is
A plain data holder (Kotlin `data class`) describing one onboarding/info screen: an illustration, a title, body text, and optionally a primary button and a link-style button, each with its own click listener. It holds no view code — it is just the content that the pager views render.
## How it fits
Built by onboarding/intro flows (e.g. logged-out upsell, feature explainers) as a `List<InfoPage>` and handed to `InfoPageAdapter` (raw pager) or `InfoPagingView.Binder.adapter()` (full screen with header and action button). `InfoPageAdapter` reads `imageResId`/`title`/`text`; `InfoPagingView` additionally reads the per-page `buttonText`/`buttonListener` and `linkButtonText`/`linkButtonListener` to update its shared bottom button and link on each page change.
## Key pieces
- `imageResId` / `title` / `text` — the required content: drawable resource for the illustration, title string, body string. WHY: everything `CaptionedImageView` needs to draw one page.
- `buttonText` / `buttonListener` — optional primary action (e.g. "Sign up"). WHY: nullable so pages without a call-to-action simply hide the shared `BoxButton` in `InfoPagingView`.
- `linkButtonText` / `linkButtonListener` — optional secondary link action (e.g. "Learn more"). WHY: drives the shared link `TextView` under the button; also nullable for the same show/hide logic.
- `uiEntityIdentifier` — optional analytics tag for the page. WHY: lets tracking distinguish which info screen was shown without coupling analytics to the view layer.
## Junior notes
- `@DrawableRes` on `imageResId` is a lint annotation, not a runtime check — passing a layout or string id still compiles but crashes at runtime when used as a drawable.
- `@JvmOverloads` generates Java-friendly constructors with default arguments, which is why Java code like `InfoPageAdapter` can call `page.getTitle()` / `getButtonListener()` as if it were a Java bean.
