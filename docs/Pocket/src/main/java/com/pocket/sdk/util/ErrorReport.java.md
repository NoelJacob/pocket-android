# Pocket/src/main/java/com/pocket/sdk/util/ErrorReport.java
## What this is
A small data holder describing an error to send to support. It bundles the crash cause, the message the user actually saw, and the name of the screen they were on. It carries context, it performs no reporting itself.
## How it fits
Created at error sites (dialog/snackbar flows) and passed to Help, which attaches it to the support request so agents know what the user experienced and where.
## Key pieces
- `activity`: screen name derived from App.getActivityContext() with "Activity" stripped; null when no foreground activity.
- `cause`: the original Throwable, if known.
- `messageSeenByUser`: the user-facing string shown in app, so support can match reports to UI copy.
## Junior notes
- App.getActivityContext() is a global lookup for the current foreground Activity; it can be null in background, so callers must handle a null activity field.
