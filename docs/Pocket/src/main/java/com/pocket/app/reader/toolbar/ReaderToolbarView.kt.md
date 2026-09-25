# Pocket/src/main/java/com/pocket/app/reader/toolbar/ReaderToolbarView.kt
## What this is
This is the actual toolbar widget at the top of the reader screen. It inflates its databound layout (ViewReaderToolbarBinding, meaning XML attributes bound to ViewModel fields) and, once setupToolbar() is called, wires taps to the given interactions and renders both state and one-shot events. It is deliberately thin: all decisions live in the delegate/ViewModel, this view only routes outcomes to Android navigation.
## How it fits
Placed in ReaderFragment's layout; the fragment calls setupToolbar() with the ViewModel's event flow, itself as readerFragment, the Listen player, the current URL, and the ViewModel as interactions/state holder. Toolbar taps flow into ReaderToolbarDelegate methods, and the ToolbarEvents it emits come back here and become real navigation: onBackPressed, AuthenticationActivity, Listen playback plus expanding the mini-player, ShareDialogFragment, OverflowBuilder popup, ItemsTaggingFragment, or a "reported" toast.
## Key pieces
- `binding`: inflated with attachToParent=true so this custom view IS the toolbar layout; lifecycleOwner plus interaction/holder bindings let the XML react to StateFlow state automatically.
- `setupToolbar(...)`: single wiring point — WHY the view needs the fragment (for childFragmentManager/activity), Listen (for playback), and url (for the share sheet) even though taps are handled elsewhere.
- Event collector (collectWhenResumed — collect the flow only while the screen is resumed): maps each ToolbarEvent to its Android action; GoToSignIn/SignIn and Share/Tag flows null-check readerFragment because the view can outlive the fragment.
## Junior notes
- collectWhenResumed avoids acting on events while the screen is in the background (e.g. firing a share dialog after the user left) — the standard way to collect UI event flows in views.
- `(activity as? AbsPocketActivity)?.expandListenUi()` is a safe cast: only Pocket activities have the mini-player; on any other host it silently skips.
