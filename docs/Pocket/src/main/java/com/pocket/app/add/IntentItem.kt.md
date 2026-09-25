# Pocket/src/main/java/com/pocket/app/add/IntentItem.kt
## What this is
A two-field data holder (Kotlin `data class`: auto-generated equals/copy) carrying what a share intent offered: the URL to save and the optional title (from the share subject). Produced by `IntentItemUtil`, consumed by `AddItemFromIntentUtil`. A null `url` is the explicit "nothing parseable" signal.
## How it fits
Sits between parsing (`IntentItemUtil.from(intent)`) and saving (`AddItemFromIntentUtil.add(item, ...)`), so parsers can evolve (new intent shapes) without touching save logic and vice versa.
## Key pieces
- `url`: nullable String; WHY nullable instead of throwing on missing input is that share sheets often contain text with no link, which must degrade to an "invalid URL" toast, not a crash.
- `title`: nullable String; attached to the save only when non-blank.
## Junior notes
- Data-class equality compares both fields; do not use it as a map key for dedupe, URL normalization lives in the sync engine (`ItemUtil.create`), not here.
