# Pocket/src/main/res/layout/fragment_authentication.xml

## What this is

This layout is the login screen: Pocket logo plus log-in / sign-up entry points.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AuthenticationFragment` (databinding = XML layouts bound to ViewModel fields, so the generated `FragmentAuthenticationBinding` class wires views to code).

ViewModels `AuthenticationViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.auth.AuthenticationViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/intro` (`com.pocket.ui.view.info.InfoPagingView`): structural container for positioning children
- `@id/authenticateButton` (`com.pocket.ui.view.button.BoxButton`): interactive element the host fragment/adapter wires up
- `@id/continueSignedOutButton` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/legalDisclaimer` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/progressBar` (`FrameLayout`): structural container for positioning children
- `@id/offlineView` (`com.pocket.ui.view.themed.ThemedConstraintLayout`): structural container for positioning children
- `@id/offlineCloseButton` (`com.pocket.ui.view.button.IconButton`): interactive element the host fragment/adapter wires up
- `@id/offlineTopImageGuideline` (`androidx.constraintlayout.widget.Guideline`): structural container for positioning children
- `@id/offlineBottomImageGuideline` (`androidx.constraintlayout.widget.Guideline`): structural container for positioning children
- `@id/offlineTopTextGuideline` (`androidx.constraintlayout.widget.Guideline`): structural container for positioning children
- ...plus 1 more ids (dividers, spacers, constraints).

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
