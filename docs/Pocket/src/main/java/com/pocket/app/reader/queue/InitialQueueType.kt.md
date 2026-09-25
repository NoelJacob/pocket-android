# Pocket/src/main/java/com/pocket/app/reader/queue/InitialQueueType.kt
## What this is
This is a two-value enum telling ReaderFragment what kind of previous/next queue to build when it first opens. Empty means no navigation; SavesList means navigate within the user's open Saves list. It is only about the initial choice — the live navigation object is a QueueManager.
## How it fits
Passed as a navigation argument into ReaderFragment, which maps Empty to EmptyQueueManager and SavesList to SavesListQueueManager. NoObfuscation (an annotation keeping the names intact) keeps the enum readable across navigation serialization.
## Key pieces
- `InitialQueueType.Empty` / `SavesList`: the two launch modes the reader understands.
## Junior notes
- Enums used as navigation args must survive serialization/obfuscation — hence the NoObfuscation marker; do not rename values without checking navigation code.
