# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/ImageViewer.java
## What this is
This is the three-panel swipe carousel behind the fullscreen image gallery: a `FrameLayout` (a container that stacks children) holding left/center/right `GalleryImageView`s that rotate roles as the user swipes. Only three views ever exist however many photos the article has — swiping reassigns them and lazily loads the new neighbor. It is the engine; `ImageViewerActivity` is the chrome around it.
## How it fits
`ImageViewerActivity` calls `setImages(images, startingImageId)` once, wires arrow buttons to `move(±1)`, and listens via `OnImageChangeListener` to refresh captions and arrow state. Swipe gestures arrive bottom-up from the center `GalleryImageView` through `shift(direction, animate)`; taps bubble through the internal `OnClick` handler to the same listener to toggle the overlay.
## Key pieces
- `setImages` — builds the `CachedImage` list (skipping uncached URLs), finds the tapped image by id, and loads center plus both followers. Filtering here is why swipe counts can differ from the article's image count.
- `shift(direction, animate)` — rotates the three views (e.g. swipe left: right becomes left with a fresh neighbor loaded, center slides right, left becomes center), snaps the new center, and notifies the listener. The rotation avoids creating or decoding more than one new image per swipe.
- `onMove(matrix)` — keeps followers glued to the dragged center image using its x offset, scaled width, and frame padding, so side photos peek correctly during a drag.
## Junior notes
- `PADDING = 10` plus `getFramePadding` defines the inter-photo gap; changing spacing means touching both this and `GalleryImageView` settle math.
- `onDestroy` nulls all three images (which recycles their bitmaps) — skipping it leaks native bitmap memory across gallery opens.
