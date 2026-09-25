# pocket-ui/src/main/java/com/pocket/ui/util/Interpolators.java

## What this is
A single shared animation easing curve (an interpolator controls how fast an animation moves at each moment; a decelerate curve starts fast and eases to a stop). Centralizing it in one constant stops every animation in the app from allocating its own identical interpolator and keeps motion feeling consistent.

## How it fits
View animations, transitions, and property animators across pocket-ui reference `Interpolators.DECEL` instead of constructing a `DecelerateInterpolator` inline. Any animated widget (expanding rows, fading badges, sliding panels) pulls this constant for its ease-out motion.

## Key pieces
- `DECEL`: one shared `DecelerateInterpolator` instance. Shared because interpolators are stateless, so a single instance serves every animation with zero per-use allocation.

## Junior notes
- `DecelerateInterpolator` with default settings eases out; if an animation needs to ease in, overshoot, or bounce, this constant is the wrong curve, not a default to force-fit.
- Sharing is safe precisely because interpolators hold no per-animation state. Do not apply that assumption to animations themselves, which must never be shared across views.
