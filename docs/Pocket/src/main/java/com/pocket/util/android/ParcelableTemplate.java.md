# Pocket/src/main/java/com/pocket/util/android/ParcelableTemplate.java

## What this is
A copy-paste starter for implementing `Parcelable` (Android's fast object serialization used to pass objects through Bundles and Intents). It solves the "Parcelable boilerplate is fiddly to write from memory" problem: it shows the required constructor-from-`Parcel`, `describeContents`, `writeToParcel`, and `CREATOR` factory in one compilable file. It is a template, not a runtime dependency — you copy it, rename it, and fill in your fields.

## How it fits
Nothing references this class at runtime (a grep shows no callers); it lives in the Android util package purely as in-repo documentation-by-example. Real parcelables in the app (navigation args, fragment arguments, saved state) follow exactly this shape. It relates to `BundleUtil.getParcelable`, which handles the class-loader pitfalls of reading parcelables back out.

## Key pieces
- `ParcelableTemplate(Parcel in)`: the deserializing constructor. WHY it exists: the framework rebuilds your object from a `Parcel` through this; read fields here in the same order `writeToParcel` writes them.
- `writeToParcel(out, flags)`: serializes fields into the parcel. WHY it exists: the mirror of the constructor — order must match exactly or data corrupts silently.
- `describeContents()`: returns 0 unless you parcel file descriptors. WHY it exists: required by the interface; almost always 0.
- `CREATOR`: the factory the framework uses to instantiate your class and arrays of it. WHY it exists: Android looks up this exact static field by name via reflection — the name and signature are non-negotiable.

## Junior notes
- Field order is the contract: write and read in identical order, or you get silently scrambled objects rather than a crash.
- Prefer `@Parcelize` (Kotlin's code-generated Parcelable) for new Kotlin classes — hand-written parcelables like this template are for Java or special cases.
- `describeContents` only returns something nonzero when the parcelable contains a file descriptor (`CONTENTS_FILE_DESCRIPTOR`).
