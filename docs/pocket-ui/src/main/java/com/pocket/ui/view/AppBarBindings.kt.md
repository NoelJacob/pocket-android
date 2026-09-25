# pocket-ui/src/main/java/com/pocket/ui/view/AppBarBindings.kt

## What this is
A single databinding adapter (a small function that teaches XML layouts how to set a custom attribute from a ViewModel field) that lets layout XML set an `AppBar`'s title declaratively. Instead of calling `appBar.bind().title(...)` in code, a layout can write `app:title="@{viewModel.uiState.title}"` and the title updates whenever the bound string changes. It is only 7 lines: the whole file is the adapter.

## How it fits
It targets the legacy View-system `AppBar.java` (the `AppBar` class in the same package) — the `appBar: AppBar` parameter means this adapter only fires on that widget. The known consumer is `Pocket/src/main/res/layout/fragment_home_details.xml`, which binds `app:title="@{viewModel.uiState.title}"` on its header. Databinding (XML layouts bound to ViewModel fields, so UI updates automatically) routes the `title` XML attribute to `setAppBarTitle()`, which delegates to the existing `AppBar.bind().title(text)` builder — no new logic, just wiring.

## Key pieces
- `setAppBarTitle(appBar, text)`: the `@BindingAdapter("title")` function — WHY it exists is to bridge the `app:title` XML attribute to the imperative `Binder.title()` API so ViewModel-driven screens never touch the view in code.

## Junior notes
- `@BindingAdapter("title")` registers the custom `app:title` namespace attribute; without this annotation databinding would not know what `app:title` means on an `AppBar`.
- The parameter type matters: databinding picks this adapter only when the view is an `AppBar`, so a `title` attribute on any other view is unaffected.
