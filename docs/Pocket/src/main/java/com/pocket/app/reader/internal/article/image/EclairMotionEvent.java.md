# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/EclairMotionEvent.java
## What this is
This is a thin subclass of `WrapMotionEvent` that exposes real multi-touch pointer data (`getX(i)`, `getY(i)`, pointer count/ids) on platforms new enough to support it. It exists so pinch-to-zoom in the article image viewer can track two fingers; on older platforms the base class degrades to single-touch. Two sentences of history, one line of function: this is a compatibility shim.
## How it fits
`WrapMotionEvent.wrap(event)` tries to construct this class and falls back to the base on `VerifyError`; `GalleryImageView`'s touch handler only ever sees the `WrapMotionEvent` type, so zoom code never branches on OS version directly.
## Key pieces
- The four overrides (`getX/getY/getPointerCount/getPointerId`) — each delegates straight to `MotionEvent`; their only purpose is to replace the base class's single-touch stubs (which throw past index 0) with real values.
## Junior notes
- The `// OPT` comment notes this could merge into the base class now that the app's min SDK is past 2.0 — true, but deleting it requires updating `WrapMotionEvent.wrap` too.
