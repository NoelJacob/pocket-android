# Pocket/src/main/java/com/pocket/sdk/util/DeepLinks.java
## What this is
The central registry for in-app destinations: factory methods that build Intents (Android messages that open a screen) for home, settings, reader, listen, premium, and topics, plus a Parser that turns URLs into those Intents. It is the single place that decides what a getpocket.com or pocket:// link opens.
## How it fits
Called from push notifications, widgets, PocketUrlHandlerActivity (which receives tapped links), and in-app link taps. Factories produce Intents consumed by MainActivity, Reader, ListenDeepLinkActivity, and PremiumSettingsActivity. Parser delegates per-link matching to an internal Link table over SUPPORTED_LINKS and SUPPORTED_MOBILE_LINKS.
## Key pieces
- `newXxxIntent` factories (newPocketIntent, newReaderIntentFromTrustedUrl, newListenIntent, newPremiumIntent, etc.): build correctly-flagged Intents; reader factory restricts external URLs for security.
- `Parser.parseLinkOpenedInReader / parseLinkOpenedInPocket / parseLinkFromExternalApplication`: three trust levels — Reader (may return null to just load in place), Internal (safe to open anything), External (untrusted, some links blocked).
- `Parser.isPocketWebUrl / isPocketMobileUrl / isPocketSaveUrl / isReaderPath`: URL classifiers for getpocket.com/pocket.co vs pocket:// scheme vs /app/read reader paths; honors no_app_intercept opt-out.
## Junior notes
- Intent = request to start a screen; intent filters in the manifest decide which Activity receives a URL. When adding a deep link, update PocketUrlHandlerActivity's filters too.
- Source matters for security: external links must never open the reader directly with attacker URLs — use the External entry point.
