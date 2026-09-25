# Pocket/src/main/res/layout/activity_premium_purchase.xml

## What this is

This layout is the Premium purchase screen with plan options.

It defines the full view hierarchy for that screen: what the user sees on first paint, including loading, content, and error/empty regions where applicable.

## How it fits

Standalone view hierarchy referenced by name from code or navigation.

## Key pieces

- `@id/appbar` (`com.pocket.ui.view.AppBar`): structural container for positioning children
- `@id/content` (`com.pocket.ui.view.visualmargin.VisualMarginConstraintLayout`): structural container for positioning children
- `@id/title` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/info` (`com.pocket.ui.view.info.InfoPagingView`): structural container for positioning children
- `@id/purchase_button` (`com.pocket.ui.view.button.PurchaseStateButtons`): interactive element the host fragment/adapter wires up
- `@id/purchase_terms` (`com.pocket.ui.view.themed.ThemedTextView`): content region updated by the host
- `@id/flow_upgrade_web_layout` (`com.pocket.app.premium.view.PremiumUpgradeWebView`): content region updated by the host
- `@id/progress` (`com.pocket.ui.view.progress.FullscreenProgressView`): structural container for positioning children

## Junior notes

- Contains a `WebView`: article HTML is loaded at runtime, so preview shows nothing; debug with remote web inspection.
