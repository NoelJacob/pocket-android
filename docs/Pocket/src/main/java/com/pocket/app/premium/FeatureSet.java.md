# Pocket/src/main/java/com/pocket/app/premium/FeatureSet.java

## What this is
NOTE: this source file no longer exists in the repo (removed after the chunk list was generated). This doc records what the last generated summary described so the link does not go stale: it defined the premade feature/pricing bundles shown on the Premium upsell screen.

## How it fits
Previously used by the Premium purchase flow (`PremiumPurchase` screens) to decide which features to list and which pricing options to offer. For current behavior, see `Premium.java` (navigation) and the purchase fragments under `settings/premium`.

## Key pieces
- Former static `FeatureSet` options — WHY: canned bundles of premium features for the upsell carousel; collaborators included billing utilities and the product list.

## Junior notes
- Do not resurrect this file; the purchase UI has moved to `PremiumSettingsFragment` / the settings premium package.
