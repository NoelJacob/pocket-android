# pocket-ui/src/main/java/com/pocket/ui/view/empty/LoadableLayout.java

## What this is
A two-state container: either a loading spinner or the EmptyView (empty/error card). The user sees a rainbow progress circle while content loads, which is then swapped for either the real content (host hides this layout) or the empty/error card with title, message, retry button, and animation. A custom progress view (such as a branded loader) can replace the default spinner.

## How it fits
Wraps loadable content regions (lists, detail panes) so screens have one place for loading versus empty/error. It extends VisualMarginConstraintLayout and inflates `view_loadable`, which holds the default RainbowProgressCircleView (Pocket's circular spinner) plus an EmptyView. Hosts drive it via `bind()`: `showProgressIndeterminate()` / `showProgress(p)` for loading, `showEmptyOrError()` (which returns the EmptyView's Binder for chaining the card setup) for the terminal state, `customProgressIndicator(v)` to swap loaders, `clear()` to reset.

## Key pieces
- `bind()`: returns the Binder tied to this layout.
- `Binder.showEmptyOrError()`: hides the spinner, shows the EmptyView, and returns `EmptyView.Binder` so callers chain card setup in one statement; WHY it returns the inner binder is the empty card still needs its own title/button configuration.
- `Binder.showProgressIndeterminate()` / `showProgress(progress)`: force the default spinner back to indeterminate mode (a spinning loop with no percentage) or set a determinate value, then show the spinner side.
- `Binder.customProgressIndicator(v)`: swaps the spinner view via `PocketUIViewUtil.replaceView` (a helper exchanging one sibling view for another), copying visibility; passing null restores the default. Custom views get stretched (0x0 match-constraints) while the default wraps content, handled by the constraint-param hacks.
- `Binder.clear()`: clears the inner EmptyView, restores the default spinner, and returns to indeterminate progress.
- `showProgress()` (private): hides the empty card and shows whichever progress view is current.
- `defaultProgressView` / `currentProgressView` / `empty`: the spinner, the active loader (default or custom), and the card.

## Junior notes
- Only two states are managed here: loading or empty/error. "Loaded with data" is the host's job: hide this layout (or overlay content over it) when data arrives.
- `showEmptyOrError()` returns a DIFFERENT binder (the EmptyView's); further calls like `.title(...)` configure the card, not this layout. Keep the LoadableLayout Binder reference if you still need `showProgress()` later.
- The TODO is real: custom progress views ignore determinate values (they just show), so `showProgress(p)` only meaningfully drives the default circle.
