# Pocket/src/main/java/com/pocket/sdk/notification/push/Push.java
## What this is
Small contract for push-notification registration: check support, register, deregister, invalidate, and (on internal builds) read the current token. It says nothing about how pushes are displayed; that is `SystemNotifications` and Firebase messaging.
## How it fits
Implemented by `PktPush`; consumed via `App.push()` by settings/logout flows and by `FcmMessageService` when Firebase rotates the token. The `CxtUi` parameters carry which UI triggered the call for analytics, and `RegistrationListener` reports the async server result.
## Key pieces
- `register(cxt_ui, listener)` — enroll this device with the server to receive pushes.
- `deregister(cxt_ui)` — unenroll; called on logout or when the user opts out.
- `invalidate()` — mark the current registration stale and re-register (used on token rotation).
- `isAvailable()` / `getToken()` — whether pushes are supported right now (logged in), and the debug FCM token (null in production).
## Junior notes
- All of these are asynchronous server operations under the hood; `register()` returning does not mean the server knows about the device yet, wait for `onResult(success, message)`.
