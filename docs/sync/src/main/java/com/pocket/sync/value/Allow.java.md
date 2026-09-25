# sync/src/main/java/com/pocket/sync/value/Allow.java

## What this is

A parsing-permission flag for TypeParser.create: it controls what is allowed in the produced value, for example whether open-type fields may resolve to unknown implementations or must strictly match known Things. contains answers membership for combined flag sets. Different call paths (strict server parsing versus lenient cache restore) pass different allowances.

## How it fits

Parsers thread these through every create call; OpenParser consults them when deciding how to handle interface/variety payloads. Tests parse with explicit allowances to prove both strict and lenient paths.

## Key pieces

- `contains` — membership check for combining and testing allowance sets

## Junior notes

- Restore paths should generally be more lenient than fresh-parse paths: old caches may hold shapes the current schema renamed.
