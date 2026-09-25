# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/ImageViewerActivity.java
## What this is
This is the fullscreen photo viewer screen users land on when tapping an article image: the swipeable `ImageViewer` plus a close button, left/right arrows, a caption line, and a tap-to-hide overlay. It is dark-mode-only and works for logged-out users, since article images are readable without an account.
## How it fits
`ArticleFragment` opens it via the static `open(activity, images, startingImageId)`, which packs the `Image` list and tapped id into the Intent with `Parceller`. The activity installs them into the viewer, implements `OnImageChangeListener` to refresh captions/arrow state on every swipe (`updateUI`), and toggles the overlay chrome on image tap.
## Key pieces
- `open(...)` — the single entry point; `EXTRA_IMAGES` + `EXTRA_START_IMAGE_ID` carry the gallery, with ids clamped to ≥ 1 because article image ids are 1-based.
- `onCreate` — wires arrows to `viewer.move(±1)`, hides both arrows for single-image galleries, applies a caption text shadow for readability, and finishes immediately when no images arrive.
- `onSaveInstanceState` — persists the image list and current index so rotation returns to the same photo rather than the first.
## Junior notes
- `getAccessType() = ALLOWS_GUEST` and `FLAG_ONLY_DARK` are the two policy lines: no login gate, and the viewer always renders dark regardless of the app theme.
- `viewer.onDestroy()` in `onDestroy` recycles the bitmaps; without it, opening several galleries in a row exhausts native memory.
