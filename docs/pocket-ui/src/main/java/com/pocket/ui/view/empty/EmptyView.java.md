# pocket-ui/src/main/java/com/pocket/ui/view/empty/EmptyView.java

## What this is
The "nothing here" card: an optional animation, a title, an explanatory message, one action button, and a collapsible details line for error fine print. The user sees it when a list loads empty or fails (for example "Something went wrong" with a Retry button). Any part left unset is hidden, and exactly one button style shows at a time: the normal button or the error-styled button.

## How it fits
Inflated from `view_empty` and shown inside lists, detail screens, and LoadableLayout (which swaps between this and a spinner). It extends VisualMarginConstraintLayout (a ConstraintLayout aware of font visual margins). Hosts configure it through the fluent Binder (a builder whose methods return itself for chaining): `bind().title(...).message(...).button(...).buttonOnClick(...).animationView(...)`; `clear()` resets everything for reuse.

## Key pieces
- `bind()`: returns the Binder bound to this view instance.
- `Binder.clear()`: blanks title, message, button, and details, clears click listeners, and removes the animation, so recycled views never show stale content.
- `Binder.title()` / `message()`: set or hide each label via `setTextOrHide` (a helper that hides the view when text is null).
- `Binder.button()` / `errorButton()`: set the normal or the error-styled button text; each hides the other variant, which is WHY only one ever appears.
- `Binder.buttonOnClick()` / `buttonOnLongClick()`: attach the handler to BOTH button variants so the action works regardless of which style is showing; long-click also toggles long-clickable so null listeners do not leave a dead long-press target.
- `Binder.details()`: sets the fine-print line and mirrors its visibility onto the divider above it.
- `Binder.animationView()`: swaps any ThemedLottieAnimationView (a theme-aware Lottie animation player) into the animation slot and starts playback immediately.
- Inflated children (`title`, `message`, `button`, `errorButton`, `details`, `detailsDivider`, `animationContainer`): looked up once in `init()` from `view_empty`.

## Junior notes
- `button()` and `errorButton()` are mutually exclusive by construction: calling one clears the other, so check which variant you set when a button "disappears".
- `setTextOrHide` means null always hides; there is no need to toggle visibility manually around these setters.
- The animation starts playing on bind, not on attach; binding an animation to an off-screen EmptyView still runs it, so bind late or reuse the view.
