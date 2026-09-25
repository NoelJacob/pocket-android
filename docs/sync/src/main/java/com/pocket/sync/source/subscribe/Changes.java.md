# sync/src/main/java/com/pocket/sync/source/subscribe/Changes.java

## What this is

Describes which Thing changes a subscription cares about: factory methods (of overloads) build an immutable selector for one Thing, many Things, or refined conditions, with value/when helpers narrowing further (for example only when a specific field changes). ThingMatch/ChangeMatch/Value nested types express the matching rules. Immutability means a Changes instance can be shared and reused safely.

## How it fits

Screens pass one of these to subscribe() or bind() to declare interest (the saved-item detail screen watches its item's Changes), and Subscribers.publish uses them to route each Space update to exactly the observers that match.

## Key pieces

- `of(...) overloads` — constructors for watching a specific Thing, a set of Things, or a broader condition
- `value/when` — refinements narrowing delivery to particular fields or value conditions
- `ThingMatch/ChangeMatch/Value` — the match-rule types the publish path evaluates per update

## Junior notes

- Subscribe as narrowly as the screen needs: broad subscriptions re-render on irrelevant changes and waste main-thread time.
