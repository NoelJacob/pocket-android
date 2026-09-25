# Pocket/src/main/java/com/pocket/app/home/views/SignInBanner.kt
## What this is
This is the "Sign in to get the best of Pocket" banner shown to logged-out users on Home. It is an `AbstractComposeView` — a classic Android `View` that hosts Jetpack Compose UI (Android's declarative UI toolkit) inside — so it can sit in the existing XML layout while its content is written in Compose.
## How it fits
Home's XML includes this view; when the user taps the Continue button, `onSignInClick` (a plain lambda the hosting fragment sets) fires and the host navigates to sign-in. `Content()` picks Compact (title over full-width button) vs Expanded (title left, 200dp button right) from the Material3 adaptive `WindowWidthSizeClass`, so foldables/tablets get the side-by-side layout automatically.
## Key pieces
- `SignInBanner` view class — `@JvmOverloads` constructor trio for XML inflation; `onSignInClick` is the only external API.
- `Content()` — wraps everything in `PocketTheme` (Pocket colors/typography) and maps COMPACT to `Layout.Compact`, everything wider to `Layout.Expanded`.
- `SignInBannerCompact` / `SignInBannerExpanded` — the two arrangements sharing title + button slots; strings come from resources (`home_sign_in_banner`, `ac_continue`).
- `SignInBannerPreview` / `SignInBannerExpandedPreview` — IDE `@Preview` renders (1342 area) for both layouts; previews only, never shipped.
## Junior notes
- `currentWindowAdaptiveInfo()` requires the Material3 adaptive dependency — don't replace with a manual width check; the adaptive API also handles折叠/foldable postures.
- This is the only Compose UI in this chunk's Home views (siblings are XML/custom views) — if you need to change banner styling, you edit Kotlin here, not a layout XML.

