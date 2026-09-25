# Pocket/src/main/java/com/pocket/app/settings/account/AccountManagement.kt
## What this is
This file holds the account-management and delete-account flows: a simple settings fragment listing the Delete Account row, plus a delete-confirmation screen (fragment + ViewModel) with two checkboxes, a progress spinner, and error handling. The thin Activity wrappers let each fragment also launch full-screen on phones instead of as dialogs on tablets. Deletion runs through UserManager.deleteAccount() with the UI gated until both confirmations are checked.
## How it fits
Reached from PrefsFragment's Account management row via AccountManagementFragment.show(), which picks DIALOG vs ACTIVITY by FormFactor (screen size); its Delete row opens DeleteAccountFragment.show() the same way. DeleteAccountViewModel (Hilt-provided) watches login info to show the cancel-premium checkbox only for premium users, and onDeleteButtonClicked awaits userManager.deleteAccount() — on SyncException it emits an error event the fragment shows as a toast.
## Key pieces
- `AccountManagementActivity` / `AccountManagementFragment`: host + single-row prefs screen (delete row → DeleteAccountFragment.show).
- `DeleteAccountActivity` / `DeleteAccountFragment`: databound (XML bound to ViewModel fields) confirmation screen — checkboxes feed the ViewModel, delete button enabled state comes from UiState.deleteButtonEnabled, app-bar back and cancel buttons finish.
- `DeleteAccountViewModel`: owns UiState (cancelPremiumCheckBoxVisible/Confirmed, permanentlyDeletedConfirmed, spinner) and one-shot events; deleteButtonEnabled requires permanent-delete plus premium-cancel (when shown).
- `setupProgressDialog()`: maps deleteAccountSpinnerVisible to a ProgressDialog via distinctUntilChanged so rotation does not stack dialogs.
- `applyAnnotations()`: converts XML link annotations on the cancel-premium label into clickable spans opening help URLs.
## Junior notes
- Fragments use AbsPocketFragment's onCreateViewImpl/onViewCreatedImpl split (not the platform onCreateView) — setupViews/listeners go in onViewCreatedImpl after binding exists.
- collectWhenResumed collects flows only while resumed: events like ShowDeleteAccountError cannot fire a toast after the user left; `_binding` is nulled on destroy so always go through the non-null getter only while the view lives.
