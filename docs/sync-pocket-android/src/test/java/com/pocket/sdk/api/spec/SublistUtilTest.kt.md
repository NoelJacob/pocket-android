# sync-pocket-android/src/test/java/com/pocket/sdk/api/spec/SublistUtilTest.kt

## What this is

Boundary tests for SublistUtil.applyOffsetCount: offset+count within, exceeding, and equal-to list size, zero count, plus the remaining offset/count edge combinations. Offset/count pagination (skip N, take M) is deceptively easy to get off-by-one, and this pins every boundary so paged lists never crash or silently drop rows.

## How it fits

Pure unit tests over lists; the Get-Thing pagination paths in production rely on this helper behaving exactly so.

## Key pieces

- `applyOffsetCount() offset + count within / exceeds list size` — normal and clamped page windows
- `applyOffsetCount() offset exceeds / equals list size` — past-the-end offsets yielding empty results, never crashes
- `applyOffsetCount() count is 0` — degenerate zero-count requests
- `applyOffsetCount / applyOffsetCount / applyOffsetCount / applyOffsetCount / applyOffsetCount / applyOffsetCount / hasPreviousPages / hasPreviousPages` — further cases in this file covering adjacent behavior of the same contract

## Junior notes

- Pagination helpers fail at boundaries in production data long before they fail in happy-path tests: keep these exhaustive.
