# Pocket/src/main/java/com/pocket/sdk/image/ImageTask.java
## What this is
Base class for image background work that makes on-screen images jump the queue: it reports a priority based on whether the request still matters to the UI. Cache-only prefetches run at low priority; visible, still-valid requests run high.
## How it fits
Extended anonymously by `ImageCache` (routing and download steps) and by `ImageResizer`. Submitted to the priority `TaskPool`s (`img-route`, `img-download`, `img-resize`) created in `ImageCache`; the pool runs higher-priority tasks first. Priority is read from the wrapped `Image.Request` and its `ImageReadyCallback`.
## Key pieces
- `getPriority()` — `PRIORITY_LOW` when there is no callback or no bitmap is wanted (prefetch), `PRIORITY_HIGH` when `isImageRequestStillValid()` is still true, `PRIORITY_NORMAL` when the view has moved on (e.g. scrolled off-screen).
## Junior notes
- The callback's validity check runs on background threads and also drives scheduling, so it must be cheap and must not touch views; heavy work here slows the whole image pipeline.
