# sync/src/main/java/com/pocket/sync/source/AuthType.java

## What this is

Declares one kind of authentication or access requirement in the sync layer (for example anonymous versus logged-in access). It is a declaration of rules, not credentials: generated code and remotes consult it to decide what a given Thing or Action is allowed to do under the current login state. The concrete kinds come from the schema's auth declarations via AuthTypeGenerator.

## How it fits

Specs and remote transports check these declarations when syncing: an action requiring login will not be sent anonymously. UserRepository-level login state upstream decides which auth type is currently active.

## Key pieces

- `auth declarations` — the named access rules from the schema that generated code turns into checkable values

## Junior notes

- This describes access rules, not tokens: actual login tokens live in the auth layer, this only gates engine behavior on the declared rules.
