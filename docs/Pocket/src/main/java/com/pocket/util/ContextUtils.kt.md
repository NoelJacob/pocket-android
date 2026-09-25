# Pocket/src/main/java/com/pocket/util/ContextUtils.kt

## What this is
A one-function helper, `asFragmentActivity()`, that unwraps an Android `Context` into its hosting `FragmentActivity` (the standard Activity base class for apps using Jetpack fragments). It solves the problem that a `View`'s context is not always the Activity itself: under Hilt dependency injection (a system where constructor parameters are provided automatically), views get a `FragmentContextWrapper` instead. For example, calling `context?.asFragmentActivity()` from a bottom sheet lets the code launch another fragment that needs an Activity reference.

## How it fits
It sits in generic `com.pocket.util` and is used by UI code that only has a `Context` in hand. Known callers are `BulkEditOverflowBottomSheetFragment` and `ItemOverflowBottomSheetFragment`, which unwrap their context to call `ItemsTaggingFragment.show(...)` for tagging saves. Downstream it just hands back the Activity (or null); it creates nothing.

## Key pieces
- `asFragmentActivity()`: a Kotlin extension function on `Context` (a function that adds a method to an existing class without subclassing it). If the context is a Hilt `FragmentContextWrapper`, it unwraps one level via `baseContext`; otherwise it tries a direct cast. Returns null when the context is not hosted by a `FragmentActivity`.

## Junior notes
- `as?` is Kotlin's safe cast: it returns null instead of throwing when the type does not match, which is why this function returns a nullable type.
- Hilt (Hilt DI) generates wrapper contexts for injected views, so never assume `view.context as Activity` works; use this helper.
- A null result is normal (e.g. application context), so callers must handle it — both known callers pass the result straight into a method that accepts null.
