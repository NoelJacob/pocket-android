# pocket-ui/src/main/AndroidManifest.xml

## What this is

The `pocket-ui` library module's manifest, intentionally a one-line stub (`<manifest />`). Library modules don't declare an app identity, launcher, or permissions; they only contribute code and resources, so there is nothing to register here.

## How it fits

At build time the manifest merger pulls `pocket-ui`'s components (none) into the app's merged manifest (`Pocket/merged_manifests/.../AndroidManifest.xml`) alongside the `Pocket` module's entries. Nothing in `com.pocket.ui.view.*` needs a manifest registration — Views, drawables, and fonts are referenced from code/layouts, not from the manifest.

## Key pieces

- **Bare `<manifest />` root** — why it exists: the minimum valid library manifest; it satisfies the build system while declaring zero components, keeping all registration in the app module where installable entries belong.

## Junior notes

- **If a `pocket-ui` component ever needs a manifest entry (Activity, Service, permission), this is where it goes.** The merger will fold it into the app automatically; never duplicate it in the app manifest.
