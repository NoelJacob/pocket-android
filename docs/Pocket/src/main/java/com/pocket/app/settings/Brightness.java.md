# Pocket/src/main/java/com/pocket/app/settings/Brightness.java
## What this is
This is a tiny static holder for a custom in-app screen brightness used by the reader. setBrightness() stores a 0–1 hardware value; applyBrightnessIfSet() later pushes it onto any AbsPocketActivity's window attributes so the reading screen can be dimmer/brighter than the system setting. Software-dimming overlay code exists but is intentionally disabled.
## How it fits
The reader's display/text-settings UI calls setBrightness() when the user drags the brightness control; each reader activity calls applyBrightnessIfSet() on setup so the stored value takes effect. getBrightness() lets the settings UI show the current value. Nothing persists it — it lives for the process lifetime.
## Key pieces
- `setBrightness(hardware)`: clamps to [0.02, 1.0] — WHY the 0.02 floor: a 0 value could turn the screen effectively black with no way to recover.
- `applyBrightnessIfSet(activity)`: no-op unless a custom value was set, so non-reader screens keep system brightness.
- `mUsingCustomBrightness` / `mHardware` / `mSoftware`: static state shared across activities; software overlay path kept but unused per the OPT comments.
## Junior notes
- WindowManager.LayoutParams.screenBrightness is per-window, not system-wide — it dies with the activity, which is why every reader activity must re-apply it.
- Static mutable state means the last writer wins across activities; do not use this for anything but the reader brightness flow.
