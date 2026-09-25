# Pocket/src/main/java/com/pocket/app/reader/internal/article/image/WrapMotionEvent.java
## What this is
This is the compatibility wrapper that lets gallery touch code handle single- and multi-touch through one type. The base implementation exposes only one finger (extra pointer indexes throw), while `EclairMotionEvent` overrides the same methods with real multi-touch data. Gesture code never checks the OS version — it just calls `wrap(event)` and uses whatever comes back.
## How it fits
`GalleryImageView`'s touch handler wraps every raw `MotionEvent` with the static `wrap`, which returns an `EclairMotionEvent` on capable platforms and falls back to this base on `VerifyError` (thrown when the device lacks the multi-touch APIs). All pointer reads (`getX(i)`, `getPointerCount`, ...) then go through the wrapper.
## Key pieces
- `wrap(event)` — the factory with the try/`VerifyError` fallback; the only place platform capability is probed, so capability checks stay out of gesture math.
- `getX(i)/getY(i)/getPointerId(i)` + `verifyPointerIndex` — single-touch stubs that return finger 0 or throw past it; throwing (rather than clamping) is what surfaces misuse as a loud error instead of a wrong-finger zoom.
## Junior notes
- On modern devices the fallback path is effectively dead, but removing it means deleting `EclairMotionEvent` and the factory together — do not delete just one side.
