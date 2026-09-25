# pocket-ui/src/main/res/values/donottranslate.xml
## What this is
This file holds pocket-ui strings that must never be translated: currently the `quantity_count` (`%d/%d`, e.g. selection counter "1/3") format. The `translatable="false"` flag (an instruction to the Smartling translation pipeline to leave the string alone) keeps machine translation from mangling placeholders. 
## How it fits
Code references it as `@string/quantity_count` / `R.string.quantity_count` wherever a selection counter is shown (tag pickers, multi-select). Downstream `String.format` fills the two numbers at runtime; because it never enters translation files, every locale renders the same compact counter.
Inventory:
- `quantity_count` = `%d/%d` (translatable=false).
