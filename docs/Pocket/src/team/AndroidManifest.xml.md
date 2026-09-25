# Pocket/src/team/AndroidManifest.xml

## What this is

A one-entry manifest overlay for the internal team (alpha) build variant. It does nothing except replace the app label with `@string/nm_icon_alpha` ("Pocket Dev"), so the alpha build installs alongside production with a distinct name. It declares no components, permissions, or features of its own.

## How it fits

At build time the manifest merger layers this overlay on top of `Pocket/src/main/AndroidManifest.xml`: the `tools:replace="android:label"` directive wins over the base `android:label`, and the merged output lands in `Pocket/merged_manifests`. The `@string/nm_icon_alpha` value it references lives in `Pocket/src/team/res/values/strings.xml`. Internal testers launch the "Pocket Dev" icon; everything else (screens, services, receivers) is inherited unchanged from the base manifest.

## Key pieces

- **`<application android:label tools:replace>`** — why it exists: the only supported way to rename one variant without forking the whole manifest; `tools:replace` explicitly tells the merger this override is intentional rather than a conflict error.

## Junior notes

- **Build variants layer manifests; they don't replace them.** Files under `src/team` (or any flavor dir) are merged with `src/main`, so a tiny overlay like this is the normal pattern, not a full copy.
