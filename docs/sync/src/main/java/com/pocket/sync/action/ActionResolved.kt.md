# sync/src/main/java/com/pocket/sync/action/ActionResolved.kt

## What this is

Helper for the reply half of an Action: some mutations return data (for example saving may return the created item), and the schema's resolved section declares that return type. This interface gives generated Action classes standard helpers for parsing that expected reply type out of server JSON. Actions with no return type simply expose a null resolved parser.

## How it fits

Used by generated Action classes (via Action.resolved()) and by remote transports after a send: the transport feeds the server response through these parse helpers and hands the resulting Things back to the source for imprinting into the Space.

## Key pieces

- `resolved-type parse helpers` — turn the raw server reply for one action into typed Things using the schema-declared return shape

## Junior notes

- If your new mutation returns nothing, there is no resolved work to do; only mutations with a resolved section in the schema need these helpers.
