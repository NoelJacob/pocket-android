# Pocket/src/main/res/values/leakcanary.xml

## What this is

This is a one-flag LeakCanary config: it hides the LeakCanary launcher icon (`false`) so the memory-leak detector stays silent in normal builds while remaining available.

## How it fits

Read by the LeakCanary library at startup in debug builds; release builds strip the library. Junior developers can ignore this file.
