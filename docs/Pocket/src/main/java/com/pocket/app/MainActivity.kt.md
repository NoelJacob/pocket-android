# Pocket/src/main/java/com/pocket/app/MainActivity.kt
## What this is
The app's single host Activity: the one Android screen that stays alive while the user moves between Home, Saves (My List), Settings, and Reader. It inflates `ActivityMainBinding`, binds `MainViewModel` via databinding (XML layouts bound directly to ViewModel fields), hosts the Jetpack Navigation graph (`NavHostFragment`), and translates ViewModel events into navigation, dialogs, and snackbars. It also owns the notification-permission primer and the "wait for first sync" gate.
## How it fits
Created after login (or into the signed-out experience) via `UserManager.getDefaultActivity()`. `MainViewModel` emits one-shot `Event`s; this activity renders them: `GoToHome/Saves/Settings` navigate via the current fragment's directions, `OpenReader` opens the reader (and optionally auto-plays Listen audio), progress/toast events show dialogs and snackbars. Incoming intents with `EXTRA_DESTINATION` (from notifications, widgets, shortcuts) are routed through `handleDeepLink` to the same ViewModel entry points.
## Key pieces
- `onCreate`: binds layout to the VM, starts event collection under `repeatOnLifecycle(STARTED)`, installs the nav-destination listener, handles the launch deep link; WHY binding gets a lifecycleOwner is so XML-bound flows auto-update and stop with the activity.
- `setupEventsObserver`: the event-to-UI switch; WHY navigation picks directions from `currentFragment` is that each tab owns its own nav actions in the graph, so "go to Reader" differs per origin.
- `handleDeepLink`: pops to the graph start, then re-posts to map `DeepLinkDestination` (HOME/SAVES/SETTINGS/TOPIC_DETAILS/READER) onto VM calls; double-posted on the main handler so the back-stack pop completes first.
- `setupDestinationListener`: reports every nav-destination change back to `viewModel.onNavigationDestinationChanged`, keeping the bottom bar and last-tab pref in sync.
- `disableInteractionUntilDataIsFetched` / `onFetchComplete`: `FetchingDialog` overlay blocks taps until the first sync lands, then reveals the nav host and triggers the notification prompt.
- `showNotificationPermissionsRequest`: Android 13+ primer dialog shown once (tracked by `notification_permission_requested` pref), launching the system permission only on positive tap.
- `DeepLinkDestination` + `EXTRA_*`: the intent contract other entry points use to land on a specific tab, topic, or reader URL.
## Junior notes
- Navigation uses `navigateSafely` (a guard against double-tap/colliding navigations); always navigate through it, never raw `navController.navigate`, or rapid taps will crash.
- `onBackPressed` is deprecated-overridden deliberately: `BackPressedUtil` gets first refusal (dialogs/sheets), then the nav back stack, then the superclass; route new back behavior through fragment callbacks, not here.
- `_binding` is nulled in `onDestroy` and event collection is lifecycle-scoped, so never hold `binding` in a launched coroutine that outlives the activity.
