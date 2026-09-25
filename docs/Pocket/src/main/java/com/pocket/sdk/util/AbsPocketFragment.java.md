# Pocket/src/main/java/com/pocket/sdk/util/AbsPocketFragment.java
## What this is
The base Fragment (a reusable piece of a screen) for every Pocket page opened via showPage. It can render either as a full embedded page or as a dialog on tablets, and it hooks up analytics screen identifiers. Subclasses implement onCreateViewImpl instead of the framework onCreateView.
## How it fits
Hosted inside an AbsPocketActivity (required parent) and swapped by PocketFragmentManager. It produces the fragment's root view, optionally wrapped in a DialogSizeWrapper on large screens, and reports CxtView/UiEntityIdentifier values downstream to analytics.
## Key pieces
- `onCreateViewImpl`: abstract inflate-only method subclasses implement; keeps view creation separate from binding.
- `onViewCreatedImpl`: binding hook (findViewById, listeners); also fires screen-view analytics when an identifier is provided.
- `onCreateDialog`: dialog path — builds an AlertDialog around the same impl view, wrapped for tablet sizing.
- `getActionViewName` / `getScreenIdentifier`: analytics hooks identifying which screen/view this fragment represents.
- `app()` / `pocket()`: deprecated accessors for the app singletons; new code should use Hilt DI (constructor parameters provided automatically) instead.
## Junior notes
- Fragment = portion of UI hosted by an Activity with its own lifecycle (onAttach, onCreateView, onViewCreated). Never override onCreateView/onViewCreated here — use the Impl variants or the dialog path breaks.
- Requires an AbsPocketActivity parent; attaching elsewhere only logs a warning in debug builds.
