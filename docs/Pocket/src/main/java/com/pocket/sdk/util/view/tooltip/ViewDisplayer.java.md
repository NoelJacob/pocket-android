# Pocket/src/main/java/com/pocket/sdk/util/view/tooltip/ViewDisplayer.java
## What this is
The minimal interface for "something that can hold and show tooltip views": set one view, dismiss it when done. It decouples the tooltip layout logic in `TooltipViewsHolder` from wherever the overlay actually lives in the view hierarchy.
## How it fits
`Tooltip.TooltipController` creates a `ViewGroupDisplayer` (the only implementation) wrapping the chosen parent, and hands it to `TooltipViewsHolder`, which calls `setView()` once and `dismiss()` at the end. Custom display targets (dialogs, overlays) would implement this interface instead.
## Key pieces
- `setView(view)` — installs the tooltip's frame into the host. WHY: the holder builds its own `FrameLayout`; the host only needs to attach it.
- `dismiss()` — final visual removal with no re-show expected. WHY: dismissal runs exit animations first, so the host must not recycle the view afterward.
## Junior notes
- There is exactly one `setView` call per tooltip lifetime; implementations should not expect re-binding.
- `dismiss()` is always called exactly once per controller, including when the tooltip never visibly showed, so hosts must handle the never-shown case (the `ViewGroupDisplayer` remove is safe there).
