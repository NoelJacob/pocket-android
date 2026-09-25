# Pocket/src/androidTest/java/com/pocket/app/list/add/AddUrlBottomSheetFragmentTest.kt

## What this is

An on-device UI test (androidTest) for the `AddUrlBottomSheet` Compose bottom sheet — the "save a URL" sheet in `com.pocket.app.list.add` — covering its title, hint, save button, hint-hiding, and error states. It drives the real composable with Compose UI test APIs rather than unit-testing a ViewModel.

## How it fits

It exercises `AddUrlBottomSheet` (the overload taking `textFieldValue`/`onTextFieldValueChange`/`onSaveButtonClick`/`isError`), which `AddUrlBottomSheetFragment` hosts and `AddUrlBottomSheetViewModel` feeds in production. The expected strings come from `R.string.add_url_title`, `R.string.add_url_hint`, `R.string.add_url_error`, and `com.pocket.ui.R.string.ac_save_to_pocket`, so a copy change here fails loudly.

## Key pieces

- **`composeTestRule = createAndroidComposeRule<ComponentActivity>()`** — why it exists: boots a real Activity on the device/emulator and gives a Compose test harness (`setContent`, `onNodeWithText`) scoped to it. Required because this is an instrumented test, not a JVM unit test.
- **`initialState_displaysTitle` / `displaysHint` / `displaysButton`** — why they exist: pin the empty-state contract (title visible, hint visible, save button visible *and* clickable via `assertHasClickAction`) so a redesign can't silently drop the save affordance.
- **`textFieldNotEmpty_hidesHint`** — why it exists: verifies the hint is a true placeholder that disappears after `performTextInput("input")` on the node with a set-text action, using local `remember` state instead of the ViewModel.
- **`isError_displaysError`** — why it exists: verifies the invalid-URL path surfaces `add_url_error` text on the error-semantics node (`SemanticsProperties.Error` + `assertTextContains`), which is what screen readers announce.

## Junior notes

- **Compose tests find nodes by semantics, not IDs.** `onNodeWithText` matches what the user sees; if a string moves into a different composable the test breaks even though no ID changed.
- **`remember` + callback wiring replaces the ViewModel here.** The test passes `textFieldValue`/`onTextFieldValueChange` lambdas directly, so failures point at the composable, never at ViewModel logic.
