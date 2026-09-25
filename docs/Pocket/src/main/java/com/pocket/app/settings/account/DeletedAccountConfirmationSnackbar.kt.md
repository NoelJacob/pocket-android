# Pocket/src/main/java/com/pocket/app/settings/account/DeletedAccountConfirmationSnackbar.kt
## What this is
This is a factory for the "account deleted" snackbar (a small dismissible banner at the bottom of the screen) shown after deletion completes. It displays a title, an explanatory message, and an action button that opens the Pocket exit survey in a browser. The caller can pass a callback to learn whether the survey URL actually opened.
## How it fits
Shown by whatever screen handles post-deletion (after DeleteAccountViewModel's delete succeeds and the user is logged out). It builds a PktSnackbar of DEFAULT_DISMISSABLE type via the fluent bind() API, wires the action to App.viewUrl with ExitSurveyUrl, dismisses itself, and reports the boolean result to onExitSurveyActionClick.
## Key pieces
- `make(activity, onExitSurveyActionClick)`: the only entry — WHY an object factory instead of a class: no state, just one configured PktSnackbar per call.
- `ExitSurveyUrl`: the Alchemer survey link; changing the survey means changing this one constant.
## Junior notes
- PktSnackbar.make needs an Activity (not just a Context) to anchor to the window — do not pass an application context.
- onExitSurveyActionClick is optional (defaults null): pass it only when you need to log whether the user reached the survey.
