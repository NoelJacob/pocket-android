# Pocket/src/main/java/com/pocket/util/DrawableLoader.kt

## What this is
A one-method interface that loads drawable (image/graphic) resources without handing Android `Context` objects to the caller. It solves the testability problem: ViewModels should not touch `Context` directly because that makes plain unit tests need Android. For example, `ItemOverflowBottomSheetViewModel` receives a `DrawableLoader` in its constructor and calls `getDrawable(R.drawable....)` while remaining a pure unit-testable class.

## How it fits
It is constructed once in `PocketModule.provideDrawableLoader()` using the application context, and provided app-wide as a singleton through Hilt DI (constructor params provided automatically). It is injected into consumers such as `ItemOverflowBottomSheetViewModel`. Under the hood it delegates to `ContextCompat.getDrawable`.

## Key pieces
- `getDrawable(drawableRes)`: the seam — returns the `Drawable` for a resource id, or null. WHY it exists: everything drawable-related in testable classes funnels through this so tests can substitute a fake.
- `invoke(context)`: a companion factory (`DrawableLoader(context)`) that binds the interface to a real Android context. WHY it exists: keeps construction in the DI module instead of scattering `ContextCompat` calls.

## Junior notes
- Hilt DI means you never call `invoke` yourself in app code; you declare a `DrawableLoader` constructor parameter and Hilt supplies it.
- `@DrawableRes` is a lint annotation: it warns at build time if you pass a non-drawable resource id.
- This mirrors `StringLoader`; the two exist for the same reason (drawables and strings are the two resources ViewModels most often need).
