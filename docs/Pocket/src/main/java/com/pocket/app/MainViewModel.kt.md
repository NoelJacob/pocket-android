# Pocket/src/main/java/com/pocket/app/MainViewModel.kt
## What this is
The ViewModel (UI-state owner that survives screen rotation; created by Hilt with `@HiltViewModel`) behind `MainActivity`'s bottom navigation. It exposes `uiState` (a StateFlow, an observable state stream the layout collects: which tab is checked, whether the bar is visible) and one-shot `events` (a SharedFlow for fire-once commands like "go to Saves"). It also remembers the user's last tab per account and resolves reader deep links (share/short URLs) into real items.
## How it fits
`MainActivity` binds to `uiState` in XML and collects `events` to navigate. Tab taps flow XML -> `MainActivityInteractions` methods -> events -> activity navigation -> destination listener -> `onNavigationDestinationChanged`, which updates state and persists the last tab. Deep-link intents land in `onReaderDeepLinkReceived`, which uses `ItemRepository` (API) to resolve `pocket.co` links, then emits `OpenReader`. Deleted-account and bad-credentials flags come from `UserManager`.
## Key pieces
- `uiState` (`UiState` + `NavigationButtonState`): checked-tab + bar visibility; WHY state vs events is that rotation must restore the bar, but must NOT re-fire navigation.
- `Event` sealed set (`GoToHome/Saves/Settings`, `Show/HideProgress`, `OpenReader`, `ShowDeletedAccountToast`, `ShowBadCredentialsToast`): one-shot commands; `extraBufferCapacity = 1` lets the VM `tryEmit` without suspending.
- `onNavigationDestinationChanged`: maps nav destinations to bar state, and persists HOME/SAVES only after the first event collection, so the restore-on-launch navigation does not overwrite the stored tab.
- `onEventCollectionStarted`: runs once per VM lifetime: restores the last tab exactly once, then emits pending account toasts; called from the activity's `onSubscription` hook.
- `onReaderDeepLinkReceived(url, openListen)`: resolves share links (by slug) and short links (by URL lookup) with progress events and silent fallback to the raw URL; `openListen` additionally fetches the item so Listen audio can start.
- `isShareLink` / `isShortLink`: `pocket.co/share/<uuid>` vs `pocket.co/<code>` shapes.
- `MainActivityInteractions`: the tab-tap interface the layout binds to; WHY an interface is so previews/tests can drive the activity without the real VM.
## Junior notes
- StateFlow (sticky current value, new collectors get it) vs SharedFlow (no replay, missed if not collecting): that is why `uiState` survives rotation but `events` must be collected with the activity started, and why navigation lives in events.
- `viewModelScope.launch` ties the link-resolution work to the VM lifetime; the try/finally around HideProgress guarantees the spinner clears even when the API throws.
- `onDeletedAccountExitSurveyClicked` is currently a no-op stub; the snackbar callback is wired but takes no action yet, so do not assume survey logic exists.
