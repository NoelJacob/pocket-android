# sync-pocket/src/main/java/com/pocket/sdk/api/endpoint/ApiException.java

## What this is

ApiException is the common error type for API failures, carrying the server error details in one place so callers handle one exception kind. AdzerkApiException specializes it for ad errors.

## How it fits

Endpoints throw it and sources/callers catch it; tests assert on it to prove error paths.

## Key pieces

- `ApiException` (class, line 12) — There was an error when connecting to the Pocket API.
- `Type` (enum, line 14) — core type of this file; callers reference it by name.
- `getXErrorCodeInt` (fun, line 46) — @return A numerical {@link #xErrorCode}, otherwise 0.
- `getUserFacingMessage` (fun, line 51) — entry point other code calls; see callers for context.
- `getMessage` (fun, line 56) — entry point other code calls; see callers for context.
- `unwrap` (fun, line 68) — Returns an ApiException if it is an ApiException or has one as a cause, or null.
- `unwrapStatusCode` (fun, line 74) — Returns the http status code of any ApiException found, or 0. See {@link #unwrap(Throwable)}
- `unwrapXErrorCode` (fun, line 80) — Returns the X-ErrorCode of any ApiException found, or 0. See {@link #unwrap(Throwable)}
- `unwrapType` (fun, line 86) — Returns the type of any ApiException found, or 0. See {@link #unwrap(Throwable)}

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.

Names you will also see here: `UserFacingErrorMessage`.
