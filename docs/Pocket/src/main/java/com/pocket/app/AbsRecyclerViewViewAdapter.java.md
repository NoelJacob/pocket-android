# Pocket/src/main/java/com/pocket/app/AbsRecyclerViewViewAdapter.java
## What this is
A base class for RecyclerView lists (Android's scrollable-list widget, where rows are recycled as you scroll) that mixes normal data rows with arbitrary raw Views such as headers, footers, or buttons in one list. Subclasses wrap their data in `Row` objects and implement `onCreateViewHolder`, calling the superclass implementation for the plain-View rows. It owns the backing list and fires the standard insert/remove notifications.
## How it fits
Extended by list adapters elsewhere in the app that need headers/footers inline with content. The subclass creates `Row` items (its own types) and `ViewHolder` binders for them; this class handles the raw-`View` rows itself via `addView`, and drives binding through `onBindViewHolder` for every position.
## Key pieces
- `Row`: abstract wrapper exposing `getViewType()`; WHY it exists is to unify data rows and raw-View rows under the single int view-type system RecyclerView requires.
- `ViewRow`: private `Row` holding a raw `View`; its view type derives from the view id so a raw view is never recycled into the wrong holder.
- `ViewHolder<T>`: abstract holder with `bind(T row)`; WHY is to give subclasses a typed bind step instead of casting in `onBindViewHolder`.
- `ViewHolderView`: holder for raw views with a no-op `bind`; returned by the base `onCreateViewHolder` (marked `@CallSuper`, so subclasses MUST call super for unknown types).
- `getViewViewType`: maps a view id to a large negative view type to avoid colliding with the usual small incrementing data view types.
- `addItem` / `addItems` / `removeItems` / `addView`: list mutation entry points; `addView` assigns a generated id if the view has none, then stores the view in `viewMap` keyed by its synthetic view type.
## Junior notes
- RecyclerView recycles row views by view type: returning the wrong holder for a type crashes or shows wrong content, which is what the negative-type trick prevents.
- If your subclass `onCreateViewHolder` does not call `super` for view types it does not recognize, raw header/footer views will crash on bind.
