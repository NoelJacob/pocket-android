# sync/src/main/java/com/pocket/sync/value/IntegerEnum.java

## What this is

Base class for generated integer-backed enums: an EnumType whose wire value is a number, as used by legacy V3-style payloads (item status codes, for example). JsonConfig.supportsIntEnums gates whether integer forms are accepted during parsing. GraphQL-side enums are name-based and use StringEnum instead.

## How it fits

EnumGenerator emits one subclass per integer schema enum (IntegerEnum in the examples); parsers map numbers to constants and serializers write numbers back. AnIntegerEnum in the test schema exercises the same path.

## Key pieces

- `integer value backing` — the numeric wire representation distinguishing these from name-based enums

## Junior notes

- Integer codes are opaque: always read them through the generated constants, never compare raw numbers at call sites.
