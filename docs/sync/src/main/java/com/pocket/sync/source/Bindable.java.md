# sync/src/main/java/com/pocket/sync/source/Bindable.java

## What this is

A Subscribeable that also offers bind: fetch the current value and subscribe for future changes in a single call. Two separate calls (read, then subscribe) leave a gap where an update can slip through unseen; bind closes that gap atomically from the caller's perspective. BindingErrorCallback reports failures of the combined operation.

## How it fits

Screens bind the Things they render (via AppSource/AsyncRemoteBackedSource implementations) so the first frame and all later updates come from one subscription. Prefer this over manual read-plus-subscribe everywhere in UI code.

## Key pieces

- `bind` — atomic initial-read-plus-subscribe that cannot miss an in-between update
- `BindingErrorCallback` — reports bind failures so the UI can show an error instead of hanging on a missing value

## Junior notes

- Every bind returns a Subscription you must stop when the screen goes away, or updates keep flowing to a dead observer.
