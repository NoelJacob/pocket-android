# Pocket/src/main/java/com/pocket/app/help/Help.java
## What this is
Builds support-request emails: subject, body with build/device/settings/troubleshooter/error diagnostics, and an optional screenshot attachment, then opens the mail client to support@getpocket.com. It is a static utility (no instance, no injection). `App.onCreate` wires it as the long-press handler on `PktSnackbar` error toasts, so any error banner can become a pre-filled bug report.
## How it fits
Any screen calls `requestHelp(type, error, context)` or the full overload; helpers concatenate device info (including sideload and WebView version), relevant settings from `PocketApp`, and the error stack into the message, attach troubleshooter data, and delegate to the `Email` utility. Nothing calls back into it.
## Key pieces
- `Type` enum: which help flavor is requested (drives subject/recipient framing).
- `requestHelp(...)` overloads: short form for error toasts, full form with explicit to/subject/message, diagnostic flags, error report, and screenshot path.
- `attachTroubleshooterData`: appends troubleshooter output and prepares attachments.
- `concatBuildAndDeviceInfo` / `concatSettings` / `concatErrorInfo` / `concatWebViewInfo`: the diagnostic sections; `isSideloaded` (null installer package = direct APK) is recorded because store vs sideload changes update behavior.
## Junior notes
- The message contains account/settings diagnostics; it is only ever composed into a user-visible email draft, never sent silently, so keep it that way.
- `getInstallerPackageName` can behave oddly on newer Android versions; `isSideloaded` is best-effort signal, not a security decision.
