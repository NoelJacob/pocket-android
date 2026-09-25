# Pocket/src/main/java/com/pocket/util/android/view/InstantChangeItemAnimator.kt
## What this is
A `RecyclerView.ItemAnimator` (the object that animates item insert/remove/change transitions) with change animations turned off. It extends `DefaultItemAnimator` but reports a zero change duration, so content updates snap instantly while add/remove animations keep working.
## How it fits
Set on lists where rebinding flashes would distract: `HomeFragment`, `DetailsFragment`, and `CollectionFragment` (in words: `recyclerView.itemAnimator = InstantChangeItemAnimator()`). It consumes change events from the adapter and produces no animation for them.
## Key pieces
- `getChangeDuration() = 0L` — WHY: the entire feature; the base class still runs its change machinery, just with no visible crossfade. Usage in words: install as the RecyclerView's item animator when refreshes should be invisible.
## Junior notes
- Only change animations are instant; add/remove/move animations keep their default durations.
- Returning 0 is safe because `DefaultItemAnimator` treats duration as timing only, not as "skip"; no extra flags are needed.

