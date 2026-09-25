# Pocket/src/test/java/com/pocket/app/list/add/AddUrlBottomSheetViewModelTest.kt
## What this is
Tests for `AddUrlBottomSheetViewModel`, the "add a URL" bottom sheet (a small slide-up panel). It proves the text field starts empty with no error, showing the sheet tracks an impression event, and saving an invalid URL shows the error state plus a save-failed event.
## How it fits
Guards production `AddUrlBottomSheetViewModel`, built from the real `Save` use case over `FakeItemRepository` and `FakeUserRepository`, with a `FakeTracker` capturing analytics. Uses `@Rule MainDispatcherRule` (JUnit4 equivalent of `BaseCoroutineTest`) for Main-dispatcher coroutines.
## Key pieces
- `subject` construction with real `Save` plus fakes — WHY: exercises real save validation without network.
- `starts with empty text field / starts without showing error` — initial UI state assertions; WHY: sheet must open clean.
- `tracks impression` — `onViewShown()` asserts `SavesEvents.addUrlBottomSheetShown()`; WHY: visibility reporting.
- Invalid-URL pair — `onTextFieldValueChange` plus `onSaveButtonClick()` asserts `textFieldIsError` and `addUrlBottomSheetSaveFailed()`; WHY: validation and its analytics stay in sync.
## Junior notes
- `MainDispatcherRule` (JUnit4 `@Rule`) and `BaseCoroutineTest` (kotlin.test hooks) do the same Main-dispatcher swap — use whichever matches the test's framework imports.
- `FakeTracker.assertTracked` requires exactly one matching event, so each test gets a fresh tracker via field initializers.
