# pocket-ui/src/main/java/com/pocket/ui/util/EmptiableView.java

## What this is
A contract for views that sometimes have no content (for example an empty text or image slot). It lets a view announce empty/non-empty transitions so a parent layout can react, typically by collapsing the view to `GONE` (fully removed from layout, unlike `INVISIBLE` which keeps its space) so its margins disappear too.

## How it fits
Custom views (labels, badges, info rows) implement this interface and fire the listener when their bound data becomes empty. Parent containers register a listener and hide or reflow accordingly. `EmptiableViewHelper` is the ready-made implementation; see it for the mechanics.

## Key pieces
- `setOnEmptyChangedListener(listener)`: the single method implementors provide. Exists so parents can observe emptiness without polling or subclassing each view.
- `OnEmptyChangedListener`: callback receiving the view and its new empty flag. Kept minimal so any parent policy (hide, dim, swap placeholder) can plug in.
- `GONE_WHEN_EMPTY`: a ready-made listener that sets the view `GONE` when empty and `VISIBLE` otherwise. Covers the overwhelmingly common case so most call sites pass this constant instead of writing a lambda.

## Junior notes
- `GONE` removes the view from layout measurement, which also collapses its margins. That is the point here, but it means siblings shift position when emptiness changes.
- This interface only declares the notification channel; the view itself must detect when its data is empty and fire the listener. Nothing here does that automatically.
