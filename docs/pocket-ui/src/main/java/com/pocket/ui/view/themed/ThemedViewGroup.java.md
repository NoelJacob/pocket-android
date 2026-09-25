# pocket-ui/src/main/java/com/pocket/ui/view/themed/ThemedViewGroup.java
## What this is
An abstract themed base for custom ViewGroups (containers that arrange child views) that do not fit the stock layout classes. It adds Pocket light/dark theme-state merging and nothing else.

## How it fits
Custom container views extend this when none of the concrete Themed layouts (constraint, linear, frame, coordinator) match their layout logic. Subclasses implement measurement and layout; they inherit theme-state merging in `onCreateDrawableState()` for free, including the four-argument constructor variant for newer style defaults.

## Key pieces
- `onCreateDrawableState()` — merges `AppThemeUtil.getState(this)` into the drawable state; the whole reason the class exists.

## Junior notes
- It is abstract because ViewGroup itself has no layout logic; you must extend it with a real container, never instantiate it directly.
- If a stock container fits, prefer the matching concrete Themed class over extending this.
