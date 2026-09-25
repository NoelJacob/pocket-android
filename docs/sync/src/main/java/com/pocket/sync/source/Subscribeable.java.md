# sync/src/main/java/com/pocket/sync/source/Subscribeable.java

## What this is

The contract for observing data: anything implementing it lets callers subscribe(Changes, Subscriber) to be told whenever matching Things change. Changes describes which Things you care about (by identity, type, or field), Subscriber receives the new versions, and the returned Subscription stops delivery. This is how the UI stays live without polling.

## How it fits

AppSource and other client sources implement this; screens subscribe to the Things they render and update on each callback. Bindable extends this with an atomic read-plus-subscribe, and Subscribers is the helper sources use to implement it.

## Key pieces

- `subscribe(Changes, Subscriber)` — registers interest in a slice of Things and returns a stoppable Subscription

## Junior notes

- Every subscribe needs a matching stop tied to the observer's lifecycle, or updates flow to dead screens and leak memory.
