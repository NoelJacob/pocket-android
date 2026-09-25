# Pocket/src/main/res/navigation/reader_graph.xml

## What this is

This is the Nested reader graph: loading, parsed article, original web, and collection destinations. It declares destinations (which Fragment class each screen id shows) and actions (named transitions between them), with the start destination shown first.

## How it fits

Hosted by `MainActivity` (main graph) or `ReaderFragment` (reader graph) through a `NavHostFragment` (the fragment that swaps destinations in and out). Screens navigate with `findNavController().navigate(R.id.<action>)`; arguments such as url or topicId travel in the generated `Args` classes.

## Key pieces

- `ArticleFragment` (`com.pocket.app.reader.internal.article.ArticleFragment`): destination reached via this graph's actions.
- `CollectionFragment` (`com.pocket.app.reader.internal.collection.CollectionFragment`): destination reached via this graph's actions.
- `InitialFragment` (`com.pocket.app.reader.internal.initial.InitialFragment`): destination reached via this graph's actions.
- `OriginalWebFragment` (`com.pocket.app.reader.internal.originalweb.OriginalWebFragment`): destination reached via this graph's actions.
