# Pocket/src/main/res/layout/activity_delete_account.xml

## What this is

This layout is the delete-account confirmation screen with its warning text and confirm action.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `AccountManagement` (databinding = XML layouts bound to ViewModel fields, so the generated `ActivityDeleteAccountBinding` class wires views to code).

ViewModels `DeleteAccountViewModel` (`viewModel`) feed observable state into the layout, and the layout calls back into them (e.g. click handlers).

## Key pieces

- `viewModel` (com.pocket.app.settings.account.DeleteAccountViewModel): databinding source; the layout reads its observable UI state and forwards user actions to it.
- `@id/appBar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/header` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/warning` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/cancelPremiumCheckbox` (`com.pocket.ui.view.button.CheckBox`): interactive element the host fragment/adapter wires up
- `@id/cancelPremiumLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/permanentlyDeleteCheckbox` (`com.pocket.ui.view.button.CheckBox`): interactive element the host fragment/adapter wires up
- `@id/permanentlyDeleteLabel` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/deleteButton` (`com.pocket.ui.view.button.ErrorButton`): interactive element the host fragment/adapter wires up
- `@id/cancelButton` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host

## Junior notes

- Databinding (`<layout>`/`<data>`): the build generates a `Binding` class; always set its lifecycle owner so observable state actually updates the UI.
