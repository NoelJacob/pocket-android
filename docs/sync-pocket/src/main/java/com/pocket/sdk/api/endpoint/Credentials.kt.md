# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/Credentials.kt

## What this is

Credentials is the small value object carrying the user's access token (and related auth fields) on every authenticated API call. Sources attach it to requests; it has no logic of its own.

## How it fits

V3Source.setCredentials and ClientApiSource.setCredentials receive it after login (see AuthenticationViewModel in the app), and every source reads it when building requests.

## Key pieces

- `Credentials` (data class, line 11) — Information about who and what is connecting to the Pocket API.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
