# Pocket/src/main/java/com/pocket/util/android/Email.java

## What this is
A one-call helper that fires off a "send an email" share intent with optional file attachments. It solves the "let the user email us content, or tell them plainly when they have no email app" problem: it picks `ACTION_SEND` vs `ACTION_SEND_MULTIPLE` based on attachments and shows a dialog pointing at support when nothing can handle the intent. For example, the Help screen calls `Email.startEmailIntent(...)` to send a support email with troubleshooter data and log-file attachments.

## How it fits
Its known caller is `Help`, which builds the recipient, subject, message body, and `Attachment` list (troubleshooter diagnostics) and hands them over. It leans on `IntentUtils.isActivityIntentAvailable` for the availability check and `ListUtils.isEmpty` for the attachment branch. Downstream it either starts the chosen email app or shows the no-email-app alert.

## Key pieces
- `startEmailIntent(to, subject, message, context, attachments)`: builds the intent (`text/plain` type, `EXTRA_EMAIL`/`EXTRA_SUBJECT`/`EXTRA_TEXT`, `EXTRA_STREAM` for attachments) and either launches it or shows the fallback dialog. WHY it exists: every email-sending path shares the unavailable-app handling.
- `Attachment`: a tiny value holder of `mimeType` plus `uri`, constructible from a `Uri` or a `String`. WHY it exists: normalizes "file to attach" into one shape regardless of how the caller holds the reference.

## Junior notes
- `ACTION_SEND_MULTIPLE` with an empty URI list misbehaves on some email apps, so the code switches intent actions on actual attachment presence — keep that branch.
- The fallback message concatenates two strings (`dg_no_email_app_m` plus the support variant), so the user still has a path forward without an email app.
- Attachment URIs shared with another app generally need read-permission grants (`FLAG_GRANT_READ_URI_PERMISSION`) at the call site — check `Help`'s URI setup if attachments arrive unreadable.
