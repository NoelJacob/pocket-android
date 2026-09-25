# Pocket/src/develop/res/values/strings.xml

## What this is

This overrides the app's launcher label for develop builds, setting `nm_icon` to `Pocket {}` with a placeholder suffix. The `{}` is filled in per build so parallel installs can be told apart on one device.

## How it fits

The `develop` source set overlays `main`: this replaces the `Pocket` label from `main/res/values/donottranslate.xml` in develop builds. The manifest reads it as `android:label="@string/nm_icon"`, so at runtime the develop app shows its suffixed name under the icon, next to production Pocket. (A separate `team` source set does the same with `Pocket Dev`.)
