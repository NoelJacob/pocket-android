# sync-pocket-android/src/test/java/com/pocket/sdk/api/source/ClientApiSourceTest.kt

## What this is

Client-API transport tests for slate lineups: getSlateLineup/getSlate sync real-shaped discovery operations through the GraphQL transport against scripted responses and assert the parsed Things. Slates are curated content rows, so this guards the discovery half of the API while PocketV3SourceTest guards the item half.

## How it fits

Uses mocked HttpHandler responses (no network); failures mean operation documents or parsing drifted from the API.

## Key pieces

- `getSlateLineup / getSlate` — lineup-fetch and slate-fetch operations end to end through the transport

## Junior notes

- Transport tests assert wire-to-Thing fidelity: update fixtures when the API adds fields, not just when tests break.
