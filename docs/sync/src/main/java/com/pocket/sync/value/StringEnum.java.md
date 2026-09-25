# sync/src/main/java/com/pocket/sync/value/StringEnum.java

## What this is

Base class for generated string-backed enums: an EnumType whose wire value is a name, as used by GraphQL endpoints and most modern payloads. It is the common case; IntegerEnum covers the legacy numeric encodings. Generated constants carry both the Java name and the wire string so renames on one side do not force renames on the other.

## How it fits

EnumGenerator emits one subclass per string schema enum (BasicEnum in the examples); parsing maps wire names to constants. Usage files track these by name, tying into the COMPAT/SKIP lifecycle.

## Key pieces

- `string value backing` — the name-based wire representation used by GraphQL-style payloads

## Junior notes

- Wire names are a compatibility surface: renaming one breaks stored data and usage tracking, so alias instead of rename.
