# Pocket/src/main/java/com/pocket/sdk/util/PocketActivityContentView.java
## What this is
The container view that holds an AbsPocketActivity's actual screen content. It is intentionally an empty FrameLayout subclass whose job is to be a strong type marking "this is the content slot" inside the larger root layout.
## How it fits
Lives inside PocketActivityRootView (inflated from ril_root); AbsPocketActivity and fragment transactions place page views into it, separate from accessory views like the Listen bar.
## Key pieces
- Constructors only: the four standard Android view constructors delegating to FrameLayout; no behavior by design.
## Junior notes
- FrameLayout = simple ViewGroup stacking children; used here as a single content slot. Its value is type safety: code taking a PocketActivityContentView cannot accidentally receive some other layout.
