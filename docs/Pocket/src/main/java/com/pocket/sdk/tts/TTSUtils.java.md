# Pocket/src/main/java/com/pocket/sdk/tts/TTSUtils.java
## What this is
This is a small dialog helper for the two dead ends of on-device speech: the device has no TTS voice data installed, or the system TTS settings screen is missing. It shows the "install voices" prompt (routing to the TTS data installer or the Play Store path) and safely opens the system TTS settings screen.
## How it fits
`TtsEngines.showPicker()` calls `showInstallRequiredDialog()` when zero engines are installed; `Listen` error handling for `NO_TTS_INSTALLED` / `INIT_FAILED` points users at `openTTSSettings()`. `onInstallResponse()` processes the result of the voice-data check intent, turning the returned voice strings into sorted `Locale` objects (a `Locale` = language+country code like en-US) for the voice picker, or re-showing the install dialog on failure.
## Key pieces
- `onInstallResponse(activity, resultCode, data)`: parses `EXTRA_AVAILABLE_VOICES` strings (`lang-country-variant`) into `Locale`s sorted by display name for picker display. Returns null and shows the install dialog when the check failed or the list is empty. WHY sorted: installer returns them in arbitrary order.
- `showInstallRequiredDialog(context)`: the install prompt with a positive button that fires `ACTION_INSTALL_TTS_DATA`, falling back to a "not supported" message when no installer handles that intent. WHY the availability check: some devices have no TTS installer at all, and firing blind would crash.
- `openTTSSettings(activity)`: opens the system TTS settings screen, or a "settings missing" dialog when the intent resolves to nothing. Same defensive pattern: never fire an intent without checking `isActivityIntentAvailable` first.
## Junior notes
- Every intent here is availability-checked before `startActivity`. Copy that pattern for any new system intent; unresolvable intents throw `ActivityNotFoundException` on some OEM builds.
- `onInstallResponse` takes the host `Activity` because dialogs need one. Don't call these with an application context; window tokens will be wrong.
- This is TTS-mode only. Streaming (server-audio) failures use `ListenError.NETWORK_ERROR`/`SERVER_ERROR` paths instead and never land here.
