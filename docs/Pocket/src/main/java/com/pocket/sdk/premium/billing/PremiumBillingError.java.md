# Pocket/src/main/java/com/pocket/sdk/premium/billing/PremiumBillingError.java
## What this is
A simplified billing error with just four buckets, so UI code can switch on a `Type` instead of parsing Google Play response codes. It optionally carries an `ErrorReport` (a log bundle) that support can use.
## How it fits
Created inside `GooglePlayBilling.createError()` from raw Play response codes, then delivered through `PremiumBillingCallbacks` to `PremiumPurchaseHelper`, which picks the dialog to show per type.
## Key pieces
- `Type` enum — `FATAL` (Play Billing unavailable, retrying is pointless), `CANCEL` (user backed out, ignore), `TEMPORARY` (probably offline, retry may work), `ALREADY_PURCHASED` (user owns it, switch to restore flow).
- `getReportableError()` — optional detail attached to a "Get Help" email; may be null (see the TODO) so callers must null-check.
## Junior notes
- `CANCEL` is not an error to display; just reset the UI to idle.
- The `TEMPORARY` vs `FATAL` split is a heuristic based on online status (`http().status().isOnline()`), not a Play guarantee.
