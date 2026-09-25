# sync/src/main/java/com/pocket/sync/source/subscribe/Subscriber.java

## What this is

The observer interface for live data: one method, onUpdate, called with the newest version of a specific Thing each time the Source learns something new about it. Implementations are typically tiny screen-level callbacks that refresh displayed fields. It never pulls: values are pushed to it as Space updates land.

## How it fits

Screens implement this (often as lambdas) when subscribing or binding to Things; Subscribers fans Space updates out to all registered instances, usually via PublishingSubscriber so delivery lands on the UI thread.

## Key pieces

- `onUpdate(Thing)` — the single push entry point delivering each new version of the watched Thing

## Junior notes

- Assume onUpdate can arrive on any thread unless wrapped: never touch views directly in a raw Subscriber.
