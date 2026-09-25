# sync/src/main/java/com/pocket/sync/value/EnumType.java

## What this is

Base class for every generated enum: wraps a raw value of type V (which must support equals, hashCode, and toString) and supplies identity semantics (getValue plus equals/hashCode/toString) once for all generated subclasses. StringEnum and IntegerEnum specialize it for the two wire encodings. Centralizing this keeps generated enums tiny and behaviorally identical.

## How it fits

EnumGenerator emits subclasses per schema enum; parsers construct them from JSON or bytes and serializers read getValue back out. BasicEnum versus IntegerEnum in the examples show the two encodings.

## Key pieces

- `getValue/equals/hashCode/toString` — the value-backed identity semantics shared by all generated enums

## Junior notes

- Enum values double as persisted identifiers in usage files: renaming a serialized value breaks restores, so treat them as stable.
