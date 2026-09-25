# Pocket/src/main/java/com/pocket/sdk/util/PermissionRequester.java
## What this is
A helper that checks and requests Android runtime permissions (e.g. storage) from an AbsPocketActivity. It reports back through a Callback with whether everything was granted, and it records that the permission was asked so "don't ask again" can be detected later.
## How it fits
Created by a screen during Activity.onCreate with a request code, callback, and permission list. It calls into ActivityCompat.requestPermissions and receives the result via the activity's lifecycle listener, then forwards the verdict to the screen's Callback.
## Key pieces
- Constructor: resolves the host via AbsPocketActivity.from and registers a SimpleOnLifeCycleChangedListener that filters onRequestPermissionsResult by request code.
- `request()`: checks each permission with checkSelfPermission; if all granted it answers immediately, otherwise launches the system prompt; always marks the requested flag.
- `Callback.onPermissionResponse`: single result method (allGranted, permissions, results) the screen implements.
- `getPermissionRequestedPref`: persisted BooleanPreference (currently only READ_EXTERNAL_STORAGE_REQUESTED) distinguishing never-asked from don't-ask-again, since the framework returns false for both.
## Junior notes
- Runtime permissions = dangerous permissions the user must approve at runtime, not install time. Must construct this in onCreate so the result listener survives Activity recreation (e.g. rotation).
