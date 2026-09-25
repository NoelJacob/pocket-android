# Pocket/src/main/res/layout/fragment_article.xml

## What this is

This layout is the parsed-article reader screen: the cleaned article body in a WebView with an end-of-article recommendations row beneath it, plus loading and error states.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `ArticleFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentArticleBinding` class wires views to code).

ViewModels `ArticleViewModel` (`viewModel`), `EndOfArticleRecommendationsViewModel` (`endOfArticleViewModel`), `FindTextViewModel` (`findTextViewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

Included or previewed by: `reader_graph.xml`.

## Key pieces

- `viewModel` (com.pocket.app.reader.internal.article.ArticleViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `endOfArticleViewModel` (com.pocket.app.reader.internal.article.recommendations.EndOfArticleRecommendationsViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `findTextViewModel` (com.pocket.app.reader.internal.article.find.FindTextViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/nestedScrollView` (`com.pocket.ui.view.themed.ThemedNestedScrollView`): scrollable container
- `@id/topSpace` (`Space`): structural container for positioning children
- `@id/webView` (`com.pocket.app.reader.internal.article.ArticleWebView`): content region updated by the host
- `@id/topDivider` (`com.pocket.ui.view.themed.ThemedView`): structural container for positioning children
- `@id/endOfArticleTitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/endOfArticleList` (`com.pocket.ui.view.themed.ThemedRecyclerView`): content region updated by the host
- `@id/loadingSpinner` (`com.pocket.ui.view.progress.RainbowProgressCircleView`): structural container for positioning children
- `@id/errorLayout` (`com.pocket.ui.view.themed.ThemedConstraintLayout2`): structural container for positioning children
- `@id/errorTitle` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/errorMessage` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- ...plus 2 more ids (dividers, spacers, constraints).

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.

- Contains a `WebView`: article HTML is loaded at runtime, so preview shows nothing; debug with remote web inspection.
