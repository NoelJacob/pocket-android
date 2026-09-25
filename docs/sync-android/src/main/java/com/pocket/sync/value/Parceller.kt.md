# sync-android/src/main/java/com/pocket/sync/value/Parceller.kt

## What this is

Kotlin sugar over Parceller.java: a putThing Bundle extension plus thingArg, a Fragment argument delegate (a property that lazily reads itself from the fragment's arguments Bundle on first access) for typed Thing arguments. Instead of manual get/put with string keys scattered across onCreate, screens declare val item by thingArg(KEY, Creator) and get a parsed, typed Thing. SyncableParser is the per-type creator that knows how to rebuild the Thing.

## How it fits

Used in Fragments that receive Things as navigation arguments; the Java Parceller still does the actual Bundle IO underneath. It pairs with Android's saved-state and navigation-argument flows.

## Key pieces

- `putThing` — Bundle extension writing one Thing under a key
- `thingArg` — read-only Fragment property delegate parsing the argument Thing on first access

## Junior notes

- Argument delegates read requireArguments(): navigating without setting the argument crashes at first access, so keep key constants shared between sender and receiver.
