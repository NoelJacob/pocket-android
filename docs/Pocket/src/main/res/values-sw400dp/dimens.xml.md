# Pocket/src/main/res/values-sw400dp/dimens.xml

## What this is

This file fixes shared sizes (1 dimensions for sw400dp screens): dialog bounds, player heights, card widths, chip metrics. Centralizing them keeps spacing consistent and makes tablet overrides one-file changes.

## How it fits

Layouts and styles consume them as `@dimen/<name>`; these override `values/dimensions.xml` on matching screens (smallest-width / large qualifiers win by Android's resource precedence).

## Key pieces

- `@dimen/home_minor_card_width` (342dp): fixed size used by layouts/styles.
