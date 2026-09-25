# Pocket/src/main/java/com/pocket/util/android/SimpleTextWatcher.java

## What this is
An abstract no-op implementation of Android's `TextWatcher` (the three-callback interface for observing text input). It solves the annoyance that `TextWatcher` forces you to implement all three methods even when you care about one: you extend this and override only what you need. For example, `ItemsTaggingFragment` overrides just `onTextChanged` to refresh tag suggestions as the user types, and `WebViewTextFinder` does the same to drive find-in-page.

## How it fits
It is extended anonymously at text-input call sites across the app. Known users are `ItemsTaggingFragment` (tag autocomplete), `TagEditTextModule` (chip-editor text commits, via `afterTextChanged`), `WebViewTextFinder` (Reader find-in-page), and `ChipEditTextInternal` (chip text handling). Downstream, each subclass feeds the observed text into its own feature (suggestions, search, chips).

## Key pieces
- `beforeTextChanged(...)` / `onTextChanged(...)` / `afterTextChanged(...)`: all present but empty. WHY empty: subclasses opt into only the phase they need without stub clutter.

## Junior notes
- No need to call `super` in your overrides — the base methods intentionally do nothing (unless you are subclassing someone else's subclass that added behavior).
- The three phases differ: `before` sees the old text, `on` sees the change in progress (do not mutate the text here — it recurses), `after` gets the editable result and is the safe place to modify text.
- Register with `editText.addTextChangedListener(...)` and remember to remove it (`removeTextChangedListener`) when rebinding recycled views — `TagsAdapter` shows that cleanup.
