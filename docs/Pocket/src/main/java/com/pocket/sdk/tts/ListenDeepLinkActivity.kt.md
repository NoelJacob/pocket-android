# Pocket/src/main/java/com/pocket/sdk/tts/ListenDeepLinkActivity.kt
## What this is
This is a transparent, self-closing Activity (an Android screen with no visible UI) that handles `/listen` deep links — URLs or intents from outside the app that mean "open Pocket and start playing my list". It exists because deep-link routing must go through an Activity, even when the real work is just starting audio playback.
## How it fits
The OS launches this Activity when something opens a Listen link. In `onCreate` it calls `App.listen().trackedControls(...).play()` to start the playlist from the beginning with deep-link analytics context, then — only if Pocket wasn't already open — fires an open-app intent so the user lands somewhere sensible. It calls `finish()` immediately either way, so it never stays on the back stack. The `savedInstanceState == null` guard prevents replaying the command after a rotation or process restore.
## Key pieces
- `onCreate()`: the whole feature. Starts playback once per fresh launch, conditionally opens the main app, then finishes. WHY an Activity at all: Android delivers link intents to Activities, not to background singletons.
- `newStartIntent(context, uiContext)`: factory that packages the analytics action-context into the launch intent via `Parceller` (a helper that puts complex objects into intents). Internal callers use this to build a well-formed Listen link.
- `EXTRA_UI_CONTEXT`: the intent extra carrying where the link came from, so the resulting listen-opened event attributes correctly.
## Junior notes
- Never add UI here. It finishes in the same frame it starts; any view would just flash.
- `App.isUserPresent()` distinguishes "Pocket already open, stay where the user is" from "cold start, open the app". Reversing that check strands cold-start users on a blank screen.
- Analytics context (`CxtUi.DEEP_LINK`) is set on the tracked controls, not the intent. Dropping it silently misattributes the play event.
