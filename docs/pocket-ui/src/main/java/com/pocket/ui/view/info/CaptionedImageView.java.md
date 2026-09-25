# pocket-ui/src/main/java/com/pocket/ui/view/info/CaptionedImageView.java
## What this is
A single onboarding/info page card: a large illustration on top with a title and a short paragraph of text underneath. It has no intrinsic width, so a parent must give it a fixed width or MATCH_PARENT — it will not work with WRAP_CONTENT. Content is set through a `bind()` helper rather than direct field access.
## How it fits
Created per page by `InfoPageAdapter.onCreateViewHolder()` (one `CaptionedImageView` per pager position, wrapped in an `InfoWrap` for centering). The adapter's `onBindViewHolder()` fills it from an `InfoPage` (image resource, title, text). It inflates `R.layout.view_captioned_image` (`image`, `caption_wrap`, `title`, `text`) and extends `VisualMarginConstraintLayout`, so it participates in the app's visual-margin spacing system.
## Key pieces
- `bind()` / `Binder` — the only public API: chainable setters `image(Drawable|resId)`, `title(...)`, `text(...)`, `clear()`. WHY: keeps view setup declarative and recyclable inside a pager without exposing the inner views.
- `captionHeight(int)` — forces the title+text wrapper (`captionWrap`) to a fixed pixel height. WHY: when swiping between pages of different text lengths, all pages are pre-measured to the tallest caption (see `InfoPageAdapter.setCaptionHeight`) so the pager does not jump in height.
- `init()` — inflates `view_captioned_image` and caches the four child views. Called from every constructor so XML and code construction behave identically.
## Junior notes
- This extends `VisualMarginConstraintLayout`, a themed `ConstraintLayout` variant that trims optical top/bottom margins — you still use it like a normal `ConstraintLayout`.
- `Binder` is an inner (non-static) class holding an implicit reference to the view; never store a `Binder` longer than the view itself or you leak the whole view hierarchy.
