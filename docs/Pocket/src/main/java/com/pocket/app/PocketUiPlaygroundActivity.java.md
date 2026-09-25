# Pocket/src/main/java/com/pocket/app/PocketUiPlaygroundActivity.java
## What this is
An internal-only gallery screen showcasing UI components (palettes, item rows, skeleton loaders, placeholders, menus, snackbars, onboarding pager) in various states. It finishes immediately on production builds, so real users can never open it. There is no navigation into it from user flows; it is launched from team/debug tooling for visual QA.
## How it fits
Stands alone: `onCreate` self-guards on `app().mode().isForInternalCompanyOnly()` then inflates the playground layout and wires each demo section in `bindExamples`. The settings-gear popup applies theme/enabled/image/mode overrides and re-renders, letting designers compare light/dark, disabled, imageless, and edit states side by side.
## Key pieces
- `onCreate` guard: `finish()` for non-internal builds; WHY a runtime guard instead of manifest removal is one APK serves all channels, with the screen unreachable in production.
- `bindExamples`: the demo wiring (app bar, palette rows per theme, discover item binding, skeleton swap-on-tap, placeholder form, fullscreen preview); each block is an independent component sample.
- Override state (`overrideTheme`, `isAllDisabled`, `itemImagesState`, `itemMode`) + popup menus: re-render knobs; the broken-image URL entry demonstrates placeholder fallbacks.
## Junior notes
- Never link to this from shipped UI or notifications: its `ANY` access plus runtime guard is defense in depth, not an invitation; keep it out of the nav graph.
- Samples use hardcoded demo content (sample image URL, sample titles); do not localize or productize anything here, and do not import these strings/layouts into real screens.
