# pocket-ui/src/main/java/com/pocket/ui/view/menu/SimpleOptionsRecyclerView.java

## What this is
A minimal single-column options list for dialogs: give it a `String[]` and it renders one plain text row per option. Tapping a row reports its position back to the host. It is theme-aware and hides its own top divider so it sits flush in dialog layouts.

## How it fits
Dropped into dialog views (`DialogView` hosts) that need a quick pick-one list without building a full adapter. The host calls `setOptions(...)` to populate it and `setOnItemClickListener(...)` to handle taps (view + position). Each row is inflated from `R.layout.view_pkt_simple_list_item`, with the text in the stock `android.R.id.text1` slot.

## Key pieces
- `SimpleOptionsRecyclerView` — extends `ThemedRecyclerView`; `init()` applies the dialog background and installs a vertical `LinearLayoutManager`.
- `setOptions(String[])` — swaps in a fresh inner `Adapter` (or clears it when null).
- `setOnItemClickListener(OnItemClickListener)` — stored listener invoked with the tapped row view and its position; null-safe at tap time.
- `Adapter` / `ViewHolder` — private inner adapter; binds each string into the row's `text1` TextView and forwards row taps to the listener.

## Junior notes
- Hilt DI (constructor parameters provided automatically) is not involved here — this view builds its own `LinearLayoutManager` internally, so the host only supplies data and a listener.
- The negative top padding in `init()` is deliberate: it pulls the list up by exactly one thin-divider height to hide the first row's divider line.
