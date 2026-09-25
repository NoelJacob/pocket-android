# Pocket/src/team/res/xml/network_security_config.xml

## What this is

The network-security policy for team/debug builds: it permits cleartext (non-HTTPS) traffic and trusts both system and user-installed CA certificates. This exists so developers can point the app at local servers and intercept traffic with a debugging proxy.

## How it fits

Referenced from the base manifest via `android:networkSecurityConfig="@xml/network_security_config"`, so every HTTP stack in the app (OkHttp, WebViews, sync engine) inherits it. Entry inventory: `base-config cleartextTrafficPermitted="true"` (allows `http://` URLs that release builds should block), `certificates src="system"` (default public CAs), `certificates src="user"` (lets a dev-installed proxy CA work, per the inline comment). Never copy the user-cert trust into a release config; it would let anyone with a sideloaded CA eavesdrop on app traffic.
