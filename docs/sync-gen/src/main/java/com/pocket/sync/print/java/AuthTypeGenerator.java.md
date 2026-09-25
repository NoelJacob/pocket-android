# sync-gen/src/main/java/com/pocket/sync/print/java/AuthTypeGenerator.java

## What this is

Emits the auth-type enum from the schema's auth declarations: one constant per declared access rule (classname names the enum, enumvalue maps each declaration, getTypeSpec builds the JavaPoet type). Generated specs and remotes check these at sync time to enforce rules like login-required. Adding an access rule means declaring it in the schema and regenerating, not editing call sites.

## How it fits

Runs inside Generator.generate(); the emitted enum backs the AuthType concept at runtime. ExamplesAuthType shows sample output.

## Key pieces

- `classname/enumvalue/getTypeSpec` — naming, per-declaration constants, and JavaPoet type assembly for the enum

## Junior notes

- Auth declarations are access rules, not credentials: login tokens still live in the auth layer.
