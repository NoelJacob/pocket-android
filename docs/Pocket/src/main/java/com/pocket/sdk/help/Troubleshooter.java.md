# Pocket/src/main/java/com/pocket/sdk/help/Troubleshooter.java

## What this is
A registry where any app component can contribute diagnostics to the bug report attached to support emails. Components register themselves once; when the user files a report, `getFormattedReport()` concatenates every contributor's section into one text blob. It owns no diagnostics itself; it is just the collection point.

## How it fits
Singleton injected wherever diagnostics are produced; producers implement `Troublemaker` and call `registerTroublemaker(...)` during setup. The support/help flow calls `getFormattedReport()` at report time and attaches the result to the outgoing email. There is no downstream beyond that string.

## Key pieces
- `registerTroublemaker(Troublemaker maker)` — adds a contributor to the internal set for this app run. Exists so components opt in without the help screen knowing about them.
- `getFormattedReport()` — skips contributors with nothing to say and joins the rest as `name + report` blocks, returning null when empty. Exists to produce the user-facing text pasted into the support email.
- `Troublemaker` — the three-method contract: user-facing name, whether there is anything to report, and the report content. Exists so each component controls its own section format.

## Junior notes
- Registration lasts only for the current process (a plain in-memory set); components must re-register every launch.
- Name and report strings are user-facing and leave the device in an email, so keep them readable and free of secrets.
