# Pocket/src/main/java/com/pocket/sdk/analytics/events/PvWtEvent.java

## What this is
A small reusable wrapper for building and sending one analytics event type (`pv_wt`, page-view/with-tracking). You construct it once with the page, section, action, and type id, then call a `send` overload each time the event happens. Each send stamps the event with the current time and device context and queues it for upload.

## How it fits
Created by callers like `CacheSettingsEvents` and any screen that tracks views or taps. On `send` it grabs the app's `Pocket` instance and calls `pocket.sync(...)` with a freshly built `PvWt` action, so the event joins the same outbound action queue as saves and other mutations. The `Interaction` helper supplies the timestamp and context blob attached to every event.

## Key pieces
- `PvWtEvent(int typeId, CxtSection section, CxtPage page, CxtEvent action)` — constructor that prebuilds a base `PvWt` action fixed to the mobile view. Exists so callers define the event shape once and reuse it.
- `event` — the stored base event; each `send` clones it via `builder()` rather than mutating it, so one instance can fire many times safely.
- `send()` / `send(String pageParams)` / `send(String pageParams, CxtSource source)` / `send(..., CxtSection section)` — overload chain ending in one real implementation. Exists to keep call sites short: pass only the details that vary per firing (extra params, source, section override).
- Final `send` body — fills in `page_params`, `source`, `time`, and `context` at send time. Exists because time/context must reflect the moment of the interaction, not construction.

## Junior notes
- `pocket.sync(...)` here means "queue this action locally and upload when possible", not "send an HTTP request right now". Offline events wait in the queue.
- The `Cxt*` enums (page, section, source, view) are generated from the server schema; you pick constants, never raw strings.
