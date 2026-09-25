# sync-pocket-android/src/test/java/com/pocket/sync/space/MutableSpaceTest.java

## What this is

One-line suite adapter: extends SpaceTest overriding only instance() to construct a spec-wired MutableSpace, thereby rerunning the entire Space contract suite (remember/forget, updates, diffs, reactions, nesting) against the production implementation. This subclass-per-implementation pattern is how the codebase proves every Space honors the same contract without duplicating tests.

## How it fits

Runs the full inherited suite; a failure here (but not in sibling suites) means MutableSpace specifically broke the contract.

## Key pieces

- `instance() override` — the single seam swapping the implementation under the whole inherited suite

## Junior notes

- New Space implementations earn trust by subclassing SpaceTest exactly like this, not by writing a parallel suite.
