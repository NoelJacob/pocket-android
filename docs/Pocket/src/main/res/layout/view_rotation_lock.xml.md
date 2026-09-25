# Pocket/src/main/res/layout/view_rotation_lock.xml

## What this is

This layout is the rotation-lock toggle row (lock/unlock auto-rotate for the reader).

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Inflated by `PktRotationLockView` (databinding = XML layouts bound to ViewModel fields, so the generated `ViewRotationLockBinding` class wires views to code).

## Key pieces

- `@id/rotation_lock_toggle` (`com.pocket.ui.view.checkable.CheckableImageView`): interactive element the host fragment/adapter wires up

## Junior notes

- Preview-only `tools:` attributes never run on device; runtime text/visibility comes from code or databinding expressions.
