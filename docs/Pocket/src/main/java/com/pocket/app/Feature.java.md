# Pocket/src/main/java/com/pocket/app/Feature.java
## What this is
The abstract base for user-facing features that may be switched off per build or audience. It maps the build's `AppMode` (PRODUCTION, TEAM_ALPHA, DEV) to an `Audience` (EVERY_USER, POCKET_TEAM, ENGIES, NOBODY) once at construction, then answers two questions: `isEnabled` (allowed for this audience at all) and `isOn` (currently showing, honoring user settings too). Concrete features like premium-gated readers extend it.
## How it fits
Feature subclasses (e.g. `PremiumReader`, `ContinueReading`) are constructed with the `AppMode` and queried at decision points in UI code ("show this entry point?"). `Audience.from` is the single place where build flavors map to who sees what, so adding a new build type updates gating everywhere.
## Key pieces
- `Audience` + `from(AppMode)`: PRODUCTION to everyone, TEAM_ALPHA to team, DEV to engies; WHY an enum instead of booleans is that call sites read as intent ("team only") rather than flag soup.
- `isOn()` (final) delegating to overridable `isOn(Audience)`: the user-visible check; override the Audience-taking one when the user can toggle the feature in settings.
- `isEnabled()` (final) delegating to abstract `isEnabled(Audience)`: the hard gate; every subclass MUST decide here which audiences may ever see it.
- `getAudience()`: exposes the computed audience for custom subclass logic.
## Junior notes
- This uses the template-method pattern: call the no-arg `isOn()`/`isEnabled()` from UI code, override the Audience-taking versions in subclasses; overriding the no-arg finals is impossible by design.
- `isEnabled` true does not mean visible: always branch UI on `isOn()`, which layers user settings on top of the audience gate.
