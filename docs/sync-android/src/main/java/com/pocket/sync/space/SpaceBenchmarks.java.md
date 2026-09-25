# sync-android/src/main/java/com/pocket/sync/space/SpaceBenchmarks.java

## What this is

A rough performance harness comparing Space implementations against each other: the SpaceProvider/Variant/Test nested types define which implementations run which workloads (see compareImplementations), measuring the imprint/remember/get paths that gate UI responsiveness. Its own javadoc admits documentation is still TODO, so read the workload definitions as the spec.

## How it fits

Used ad hoc when tuning MutableSpace or evaluating a new Space implementation; it is developer tooling, not shipped app code. Results guide optimization work on the Space internals.

## Key pieces

- `compareImplementations` — runs the workload battery across Space variants and reports relative performance
- `SpaceProvider/Variant/Test` — the implementation sources, configurations, and workloads being compared

## Junior notes

- Benchmarks on a device lie if the device is thermally throttled or busy: run cool, multiple passes, and compare relatively, never absolutely.
