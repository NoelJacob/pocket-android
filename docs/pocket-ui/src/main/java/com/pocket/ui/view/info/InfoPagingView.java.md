# pocket-ui/src/main/java/com/pocket/ui/view/info/InfoPagingView.java
## What this is
A full-screen onboarding/info screen: an optional header image on top, a swipeable pager of `InfoPage` cards in the middle, page dots underneath, and a shared primary button plus link text at the bottom. The button and link are shared (not per page) and their labels/actions swap automatically as the user swipes. Touches anywhere in the content area are forwarded to the pager so the whole screen feels swipeable.
## How it fits
Hosted by onboarding/intro activities or fragments (logged-out upsell, feature explainers). The host builds a `List<InfoPage>`, wraps it in an `InfoPageAdapter`, and calls `bind().adapter(...)`. Internally it composes `PageIndicatedViewPager` (`R.id.viewPager`), a `BoxButton` (`R.id.actionButton`), a link `TextView` (`R.id.actionLinkText`), and a header `ImageView` (`R.id.header`), inflating `R.layout.view_info_paging_view`.
## Key pieces
- `InfoAdapter` (abstract static class) — minimal adapter contract: a `RecyclerView.Adapter` plus `getData(): List<InfoPage>`. WHY: the view needs the page list for button/link updates without downcasting to a concrete adapter.
- `Binder.adapter(InfoAdapter)` — installs the adapter on the inner pager and registers an `onPageSelected` callback that shows/hides and relabels the shared button and link from the newly selected `InfoPage`. WHY: one callback centralizes all per-page chrome updates; setting a new adapter clears old page listeners first so stale screens do not keep updating the button.
- `Binder.header(Drawable|resId)` — sets or hides the top header image (`GONE` when null). WHY: some flows want a brand header above the pager, others do not.
- `Binder.nextPage()` / `previousPage()` / `getCurrentPage()` — programmatic pager navigation. WHY: lets Back buttons or "Continue" actions drive the pager without touching the inner `ViewPager2`.
- Touch forwarding (`init` listener + `onTouchEvent`) — swipes starting on background areas are passed to the pager. WHY: otherwise only gestures starting directly on the card would page, which feels broken on a full-screen intro.
## Junior notes
- Call `addOnPageChangeListener()` only after `adapter(...)`, because installing an adapter clears all previously added page listeners.
- The button/link update is `post()`ed to the UI thread inside `onPageSelected` — `ViewPager2` callbacks can arrive mid-layout, so touching views directly there can crash; the `post` defers it to the next safe moment.
