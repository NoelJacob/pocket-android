# Pocket/src/main/res/values/strings.xml

## What this is

This is the app's master string table: every user-visible word in the app (468 strings, plurals, and string-arrays). Entries are grouped by `prefix_` naming (`setting_` for Settings rows, `ts_` for toasts, `dg_` for dialogs) so translators and developers can tell where copy appears.

## How it fits

Referenced from layouts as `@string/<name>` and from code as `getString(R.string.<name>)` / `resources.getQuantityString` for plurals. Translated per-locale copies live in the `values-<locale>/` siblings; adding a string here means adding it (via Smartling) there.

## Key pieces

- `setting_*` (97): Settings-screen rows, headers, and summaries (largest group) e.g. `setting_header_account`, `setting_logout`, `setting_premium`.
- `lb_*` (50): short row/tab labels across lists and dialogs e.g. `lb_tab_saves`, `lb_continue_reading`, `lb_add_copied_url`.
- `dg_*` (48): progress-dialog messages (Loading, Logging out) e.g. `dg_loading`, `dg_changing_data_location`, `dg_clearing_cache`.
- `ac_*` (32): button and menu action labels (Log in, Cancel, Share) e.g. `ac_login`, `ac_logout`, `ac_authenticate`.
- `ts_*` (22): toast confirmations (Saved!, Archived, Deleted) e.g. `ts_add_invalid_url`, `ts_add_saved_to_ril`, `ts_add_added`.
- `prem_*` (20): Premium subscription status rows e.g. `prem_setting_thanks`, `prem_setting_status`, `prem_setting_subscription`.
- `mu_*` (19): overflow-menu items (View Original) e.g. `mu_settings`, `mu_read_later`, `mu_help`.
- `tts_*` (19): text-to-speech (listen) settings and error messages e.g. `tts_dg_t`, `tts_settings`, `tts_dg_not_available_m`.
- `empty_*` (16): empty-state titles, messages, and actions e.g. `empty_list_all_title`, `empty_list_all_action`, `empty_list_favorites_title`.
- `purchase_*` (14): Premium purchase-screen copy e.g. `purchase_error_offline_t`, `purchase_error_offline_m`, `purchase_error_activation_t`.
- `listen_*` (12): listen-player strings (playback, data warnings) e.g. `listen_playing_from`, `listen_playlist_header`, `listen_data_alert`.
- `report_*` (11): report-a-story reasons and confirmations e.g. `report_item_overflow_option`, `report_item_option_1`, `report_item_option_2`.
- `add_*` (10): add-to-Pocket overlay strings e.g. `add_overlay_free_title`, `add_tags_list_empty_title`, `add_tags_list_empty_body`.
- `nt_*` (10): note/highlight strings e.g. `nt_downloading`, `nt_cancel`, `nt_channel_functional_name`.
- `nm_*` (9): screen and section titles plus read-time labels e.g. `nm_add_to_ril`, `nm_add_tags`, `nm_theme_light`.
- `storage_*` (9): offline storage-location options e.g. `storage_free_space`, `storage_free_space_unknown`, `storage_location_changed`.
- `home_*` (8): Home-screen strings (banner, See All, topics) e.g. `home_sign_in_banner`, `home_see_all`, `home_collection`.
- `onboarding_*` (6): onboarding-screen copy e.g. `onboarding_learn_more_1_title`, `onboarding_learn_more_1_text`, `onboarding_learn_more_2_title`.
- `my_*` (6): Saves-list filter strings e.g. `my_list_filter_all`, `my_list_filter_tagged`, `my_list_filter_favorites`.
- `bg_*` (4): background-sync strings e.g. `bg_offline_cache_storage_is_unavailable_t`, `bg_offline_cache_storage_is_unavailable_m`, `bg_offline_cache_is_missing_t`.
- `suggested_*` (4): suggested-tags strings e.g. `suggested_tags_empty_no_tags`, `suggested_tags_empty_none_found`, `suggested_tags_no_connection`.
- `time_*` (4): reading-time filter labels e.g. `time_to_read_filter_short`, `time_to_read_filter_short_description`, `time_to_read_filter_long`.
- `notification_*` (4): notification channel/text strings e.g. `notification_permission_primer_title`, `notification_permissions_primer_message`, `notification_permissions_primer_positive_button`.
- `delete_*` (3): app copy in this area e.g. `delete_tag_confirmation_message`, `delete_note_prompt_title`, `delete_note_prompt_message`.
- `ic_*` (2): app copy in this area e.g. `ic_highlight`, `ic_copy`.
- `tx_*` (2): app copy in this area e.g. `tx_love_pocket`, `tx_tell_others`.
- `re_*` (2): reader error title/message pairs e.g. `re_downloading_article_view_t`, `re_downloading_article_view_m`.
- `list_*` (2): app copy in this area e.g. `list_empty_search_title`, `list_empty_no_result_matched`.
- `collection_*` (2): collection-screen strings e.g. `collection_details_error_title`, `collection_details_error_message`.
- `no_*` (2): app copy in this area e.g. `no_internet_connection`, `no_internet_connection_body`.
- `edit_*` (2): app copy in this area e.g. `edit_tags`, `edit_note_title`.
- `original_*` (2): app copy in this area e.g. `original_web_no_browser`, `original_web_changed_browser_setting`.
- `clipboard_*` (1): app copy in this area e.g. `clipboard_label_url`.
- `done_*` (1): app copy in this area e.g. `done`.
- `legacy_*` (1): app copy in this area e.g. `legacy_upgrading`.
- `viewed_*` (1): app copy in this area e.g. `viewed`.
- `not_*` (1): app copy in this area e.g. `not_viewed`.
- `fxa_*` (1): app copy in this area e.g. `fxa_account_migrated`.
- `move_*` (1): app copy in this area e.g. `move_to_my_list`.
- `highlighted_*` (1): app copy in this area e.g. `highlighted`.
- `short_*` (1): app copy in this area e.g. `short_reads`.
- `long_*` (1): app copy in this area e.g. `long_reads`.
- `recent_*` (1): app copy in this area e.g. `recent_saves`.
- `highlights_*` (1): app copy in this area e.g. `highlights`.
- `end_*` (1): app copy in this area e.g. `end_of_article_title`.
- `previous_*` (1): app copy in this area e.g. `previous_and_next_setting_description`.
- `authentication_*` (1): app copy in this area e.g. `authentication_legal_disclaimer`.
