# Pocket/src/main/res/navigation/main_graph.xml

## What this is

This is the Main navigation graph: Home, Saves, Reader, details, and Settings destinations plus their actions. It declares destinations (which Fragment class each screen id shows) and actions (named transitions between them), with the start destination shown first.

## How it fits

Hosted by `MainActivity` (main graph) or `ReaderFragment` (reader graph) through a `NavHostFragment` (the fragment that swaps destinations in and out). Screens navigate with `findNavController().navigate(R.id.<action>)`; arguments such as url or topicId travel in the generated `Args` classes.

## Key pieces

- `HomeFragment` (`com.pocket.app.home.HomeFragment`): destination reached via this graph's actions.
- `SlateDetailsFragment` (`com.pocket.app.home.details.slates.SlateDetailsFragment`): destination reached via this graph's actions.
- `TopicDetailsFragment` (`com.pocket.app.home.details.topics.TopicDetailsFragment`): destination reached via this graph's actions.
- `MyListFragment` (`com.pocket.app.list.MyListFragment`): destination reached via this graph's actions.
- `NoteDetailsFragment` (`com.pocket.app.list.notes.NoteDetailsFragment`): destination reached via this graph's actions.
- `ReaderFragment` (`com.pocket.app.reader.ReaderFragment`): destination reached via this graph's actions.
- `OpenSourceLicensesFragment` (`com.pocket.app.settings.OpenSourceLicensesFragment`): destination reached via this graph's actions.
- `PrefsFragment` (`com.pocket.app.settings.PrefsFragment`): destination reached via this graph's actions.
- `AppIconSettingsFragment` (`com.pocket.app.settings.appicon.AppIconSettingsFragment`): destination reached via this graph's actions.
