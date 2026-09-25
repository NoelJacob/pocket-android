# Pocket/src/test/java/com/pocket/app/home/details/topics/TopicDetailsViewModelTest.kt
## What this is
Single-behavior test for `TopicDetailsViewModel`, the topic-detail screen. It proves tapping an item calls `ContentOpenTracker` with a `topicArticleContentOpen` event carrying the URL, position, and topic title.
## How it fits
Guards production `TopicDetailsViewModel`, opened from Home topic rows and feeding the reader. All dependencies (`TopicsRepository`, `ItemRepository`, `Save`, trackers) are relaxed MockK mocks; only the analytics call is verified.
## Key pieces
- `setup()` — builds the ViewModel with mocked repos/trackers; WHY: isolates the click-to-analytics path.
- `WHEN an item is clicked THEN an analytics event is sent` — calls `onItemClicked("url", 1, "recId")` and verifies `contentOpenTracker.track(...)` exactly once; WHY: pins the content-open contract for topic surfaces.
## Junior notes
- Note the two trackers: `Tracker` is generic events, `ContentOpenTracker` is content-open events — this screen asserts on the latter.
- Relaxed mocks mean unstubbed repository calls silently succeed; this test never exercises them.
