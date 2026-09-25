# sync/src/main/java/com/pocket/sync/thing/FlatUtils.java

## What this is

Utilities for finding identifiable sub-Things nested inside parents: flatten walks a Thing graph and collects every identifiable descendant (Base/Deep/Shallow/Output shape the traversal and its result), references lists direct child links, and addAll/add overloads accumulate across collections. The engine needs this because one server payload routinely carries a whole tree (an item plus its tags, images, authors) that must enter the Space as separate addressable Things.

## How it fits

Imprint and resolve paths use these to split incoming graphs before storage; FlatTest and SubthingsTest pin the traversal against interface, list, map, and mixed nesting. Thing.flat/subthings are the convenient entry points.

## Key pieces

- `flatten overloads` — collect every identifiable descendant of a Thing graph
- `references` — list the direct identifiable child links of one Thing
- `addAll/add` — accumulators for merging traversal results
- `Base/Deep/Shallow/Output` — traversal modes and result shapes controlling depth and detail

## Junior notes

- Flattening follows identity, not object nesting: non-identifiable embedded objects stay part of their parent by design.
