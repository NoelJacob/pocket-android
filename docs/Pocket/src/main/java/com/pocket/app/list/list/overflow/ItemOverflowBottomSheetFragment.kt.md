# Pocket/src/main/java/com/pocket/app/list/list/overflow/ItemOverflowBottomSheetFragment.kt

## What this is
The per-item "..." bottom sheet on a single save row (mark viewed, tag, archive/re-add, delete). Like the filter sheet, it is a thin UI shell: inflate, bind to the ViewModel, react to screen-state changes.

## How it fits
Opened from a list row's overflow button via `newInstance(item, savesTab)`; the tapped `Item` (a generated API model, passed as a serialized "thing" argument) and the tab travel in the fragment arguments. `ItemOverflowBottomSheetViewModel` does the work; this fragment observes `screenState` to dismiss itself or open `ItemsTaggingFragment`, and loads the header thumbnail lazily.

## Key pieces
- `newInstance(item, savesTab)` — WHY: packages the item (`putThing`) and tab enum so the sheet survives rotation.
- `onViewCreated` → `setupScreenStateObserver` + `setupImageUrlObserver` + `onInitialized` — WHY: observers must be registered before initialization emits state, or the first frame (title/thumbnail) is missed.
- `setupScreenStateObserver` — WHY: translates ViewModel states into navigation: `CLOSING` dismisses, `OPEN_TAG_SCREEN` launches `ItemsTaggingFragment` for this item then dismisses.
- `setupImageUrlObserver` — WHY: loads the header art on a background thread via `LazyAssetBitmap`/`LazyBitmapDrawable`, keyed by item add-time for cache partitioning.

## Junior notes
- `thingArg`/`putThing` serialize the API `Item` through the sync layer's JSON creator — the item is a snapshot; actions re-resolve it through `ItemRepository`.
- `collectWhenResumed` auto-cancels on pause, so rapid open/close cannot dismiss the wrong sheet instance.
