# Pocket/src/main/java/com/pocket/app/settings/OptionsDialogs.java
## What this is
This is a helper for the background-sync setting row in PrefsFragment. backgroundSyncingChange() applies the user's chosen sync mode: instant (push) mode registers for push notifications first with a progress dialog, while timer modes just set the value — except timer sync is refused when the app is installed on external storage. Every failure shows an explanatory alert and reports failure through the callback.
## How it fits
Called from PrefsFragment's background-sync multiple-choice listener with the newly picked value. On success the preference is updated (instant mode updates it inside the push-register callback); on failure the dialog stays so the user can pick again. Downstream it touches BackgroundSync.setBackgroundSyncing and App.push().register.
## Key pieces
- `backgroundSyncingChange(syncType, callback, context)`: WHY instant mode is special — it needs a push token and connectivity before the setting is valid, so it gates on isAvailable()/isOnline() and registers asynchronously.
- Failure alerts (push unsupported, offline, registration failed, timer-on-SD-card): each maps one dead end to a user-readable dialog; callback(false) always follows so the caller knows the pick did not stick.
- `showAlert(...)`: skips silently when the context is gone (AlertMessaging.isContextUnavailable) to avoid window-leak crashes.
## Junior notes
- The ProgressDialog is dismissed in the register callback, not after the call — registration is async, so dismissing eagerly would hide it while work continues.
- Returning via SimpleResultCallback (a single-method boolean callback) keeps this Java-friendly; the PrefsFragment listener uses the boolean to decide whether to dismiss the choice dialog.
