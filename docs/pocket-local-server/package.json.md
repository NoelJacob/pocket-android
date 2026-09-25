# pocket-local-server/package.json

## What this is
This is the manifest for the local development backend: its name (`pocket-local-server`), version, entry point (`server.js`), and sole dependency (Express `^4.19.2`). It declares a private, unlicensed throwaway server — not a published package — whose only job is to stand in for Pocket's production APIs during local end-to-end work.

## How it fits
From `pocket-local-server/`, `npm install` (or `bun install`) reads this file to fetch Express into the ignored `node_modules/`, and `node --watch server.js` starts the backend on `0.0.0.0:8080`. The Android app's debug builds point at that port (via `10.0.2.2` from the emulator), so this file is the root of the whole local dev loop: install → run server → build `developDebug` → fake-login and save a URL.

## Key pieces
- **`dependencies.express: ^4.19.2`** — the only dependency: a minimal REST layer for the `/v3/*` and `/parser` routes; the caret range allows patch/minor updates while the lockfile pins exact bytes.
- **`main: server.js`** — declares the server entry point so `node .` and tooling resolve to the right file.
- **`private: true` + `UNLICENSED`** — marks the package as unpublished internal tooling, blocking accidental `npm publish`.

## Junior notes
- `node --watch` restarts the server automatically when you edit `server.js` — leave it running in a terminal while you iterate on endpoint shapes.
- The emulator reaches your machine's localhost as `10.0.2.2`, so the app uses `http://10.0.2.2:8080` where your `curl` probes use `http://localhost:8080`.
