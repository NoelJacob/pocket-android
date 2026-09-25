# sync-pocket-android/src/test/java/com/pocket/sdk/api/ItemActionsSpecTest.java

## What this is

The item-action contract suite: thirteen tests pinning exactly what each user action does to local state (addByUrl/addByItem/readd for saving, archive/delete, scrolled, favoriting, and the full tag family tagsAdd/tagsClear/tagsReplace/tagsRemove/tagRename/tagDelete). If product ever asks what archive means, this file plus the Applier is the executable answer. At ~428 lines it is the most behavior-dense SDK test.

## How it fits

Runs actions through PocketSpec against scripted state and asserts the resulting Things; it guards the Spec.apply half of the save walkthrough (local effect before any network). Transport coverage lives in PocketV3SourceTest.

## Key pieces

- `addByUrl / addByItem / readd` — the three save entry points and their resulting item state
- `archive / delete / scrolled / favoriting` — lifecycle and engagement actions on saved items
- `tagsAdd / tagsClear / tagsReplace / tagsRemove / tagRename / tagDelete` — the complete tag-mutation family

## Junior notes

- New item actions need a test here first: uncovered apply logic is how silent save regressions ship.
