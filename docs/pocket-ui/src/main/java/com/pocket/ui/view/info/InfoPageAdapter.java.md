# pocket-ui/src/main/java/com/pocket/ui/view/info/InfoPageAdapter.java
## What this is
The `RecyclerView.Adapter` (the object that creates and fills each swipeable page for `ViewPager2`, Android's swipe-between-pages widget) backing the onboarding/info pager. It turns a `List<InfoPage>` into `CaptionedImageView` cards, pre-computing a single caption height so every page is the same height and the pager does not jump while swiping.
## How it fits
Constructed by onboarding screens with `(context, screenWidth, pages)` and set on `InfoPagingView` via `Binder.adapter()` or on a `PageIndicatedViewPager` directly. `onCreateViewHolder()` builds a `CaptionedImageView` inside a centering `InfoWrap`; `onBindViewHolder()` fills it from `pages.get(position)`. It extends `InfoPagingView.InfoAdapter` so `InfoPagingView` can pull the page list back out via `getData()` to drive its per-page button/link.
## Key pieces
- `InfoPageAdapter(context, screenWidth, pages)` — reads `R.dimen.pkt_info_page_max_width` into `maxPageWidth` and calls `setCaptionHeight()`. WHY: caps card width on tablets while measuring text against the real screen width.
- `setCaptionHeight(...)` — measures title + subtitle height for every page (using `Fonts` Graphik typefaces, `pkt_medium_text`/`pkt_small_text` sizes, and `TextViewUtil.getExpectedTextViewHeight`) and keeps the maximum plus padding. WHY: `ViewPager2` recycles views, so unlike the old `ViewPager` trick of keeping all pages alive, a precomputed fixed caption height is what keeps rows uniformly sized.
- `InfoViewHolder` / `InfoWrap` — holder pairing a centering `FrameLayout` wrapper with its `CaptionedImageView`. WHY: on wide screens the card stays at `maxPageWidth` centered instead of stretching full-bleed.
- `onCreateViewHolder()` — creates the card, applies `setMaxWidth(maxPageWidth)` and the precomputed `captionHeight`. WHY: height must be fixed at creation, not bind time, so recycled pages never flash at the wrong size.
- `getData()` — returns the page list to satisfy the `InfoAdapter` contract. WHY: lets `InfoPagingView` react to page changes (button/link text) without holding a second copy of the list.
## Junior notes
- `TextPaint` measurement must use the exact typeface and text size the layout uses — if you change fonts in `view_captioned_image.xml` without updating this method, the precomputed height will be wrong and text will clip or leave gaps.
- `RecyclerView.ViewHolder` positions shift during animations; always read content via the `position` passed to `onBindViewHolder`, never cache it in the holder.
