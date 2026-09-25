# Pocket/src/main/java/com/pocket/app/home/topics/TopicsAdapter.kt
## What this is
This is the RecyclerView adapter for Home's topic pill row (the horizontally scrolling list of topics like "Tech" or "Health"). Each row is a single topic chip (`ViewHomeTopicItemBinding`) showing its display title; tapping it opens that topic's details screen.
## How it fits
Created by the Home screen with the shared `HomeViewModel`. Its `init` block collects `viewModel.topicsUiState` (scoped to CREATED via `repeatOnCreated`) and submits to the `ListAdapter`. Binds call `viewModel.onTopicClicked(topicId, title)`, which navigates to `TopicDetailsFragment` with the topic ID.
## Key pieces
- `TopicViewHolder.bind(state)` — sets `topicTitle.text` and forwards taps with both `topicId` (navigation key) and `title` (display/analytics).
- `DIFF_CALLBACK` — compares whole `TopicUiState` objects for both identity and contents; simple and correct for a short, rarely-reordered list.
## Junior notes
- A `ListAdapter` + `DiffUtil` is arguably heavy for a handful of chips, but it matches every other Home adapter — consistency wins over micro-optimization here.
- Taps go through the shared `HomeViewModel`, not a callback lambda — test navigation by verifying `onTopicClicked`, not adapter internals.

