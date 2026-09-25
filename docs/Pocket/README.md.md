# Pocket/README.md

## What this is

This is the landing-page doc for the `Pocket` Gradle module, the flagship Android app (Play listing `com.ideashower.readitlater.pro`). It maps the four top-level packages (`com.pocket.app`, `com.pocket.sdk`, `com.pocket.sdk2`, `com.pocket.util`) and points newcomers at the core subsystems: API/sync, offline caching, threading, account, and collection views.

## How it fits

This file is the first thing a developer reads before touching anything under `Pocket/src`. It sends screen/feature work to `com.pocket.app` (Activities, Services), shared Pocket logic to `com.pocket.sdk` (with `com.pocket.sdk2` hosting sync-refactor experiments), and generic helpers to `com.pocket.util`. Downstream it links out to the Pocket platform Spec and to the sync, `Assets`/`OfflineDownloading`, `AppThreads`, `Pocket.user()`, and `DataSourceView` entry points.

## Key pieces

- **Package map (`com.pocket.app` / `sdk` / `sdk2` / `util`)** — tells you which tree owns a bug: screens go in `app`, reusable Pocket behavior in `sdk`, throwaway refactor experiments in `sdk2`, non-Pocket helpers in `util`. Read this before grepping blindly.
- **Pocket API section (Spec + `com.pocket.sdk.api`)** — why it exists: every server concept (items, tags, sync actions) is defined cross-platform in the Spec, so the doc points there first to keep Android code consistent with iOS/web.
- **Offline / caching section (`Assets`, `OfflineDownloading`, `Image`)** — why it exists: article and image downloading is centralized instead of per-screen, so reader and list screens share one cache.
- **Async / account / views pointers (`AppThreads`, `Pocket.user()`, `DataSourceView`)** — why they exist: one blessed threading helper, one logged-in-user accessor, and one collection-view driver, so features don't reinvent them.

## Junior notes

- **Start in `com.pocket.app` when hunting a screen.** If you can see it on screen, its Activity/Fragment lives there; `sdk` and `util` are supporting cast.
- **`sdk2` is allowed to be messy.** It is explicitly the experiments drawer for the sync refactor, so expect duplication with `sdk` rather than a clean API.
