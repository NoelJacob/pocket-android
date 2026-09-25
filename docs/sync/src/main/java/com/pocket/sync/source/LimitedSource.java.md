# sync/src/main/java/com/pocket/sync/source/LimitedSource.java

## What this is

A marker for sources that only support a subset of Things and Actions, such as a remote endpoint that knows about saves but not about discovery content. It warns generic engine code not to assume every operation will work everywhere. The javadoc itself muses that this capability info might one day move into the Spec API.

## How it fits

Remote transports implement this when their endpoint is partial, and higher layers check it before routing work (for example sending only supported actions to a given remote). Full-capability sources like AppSource do not need it.

## Key pieces

- `limited-subset marker` — the signal that callers must check support before issuing arbitrary Things/Actions here

## Junior notes

- Hitting unsupported-operation errors against a remote usually means this marker's warning was ignored at the routing layer.
