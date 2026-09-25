# sync-pocket/src/main/java/com/pocket/sdk/network/eclectic/EclecticHttp.java

## What this is

EclecticHttp is the interface abstracting HTTP execution so sources depend on it instead of OkHttp directly, which keeps transports testable with fakes. EclecticOkHttpClient is the real implementation.

## How it fits

Every Source and endpoint handler receives one at construction; tests swap in recorded or fake implementations.

## Key pieces

- `EclecticHttp` (interface, line 16) — A generic interface for interacting with a Network/Http client.
- `ResponseParser` (interface, line 53) — core type of this file; callers reference it by name.
- `Stream` (interface, line 66) — Your choice of how to work with the data. Only call one method, it is an error to call more than one.
- `Response` (interface, line 71) — core type of this file; callers reference it by name.
- `Logging` (enum, line 79) — core type of this file; callers reference it by name.
- `buildRequest` (fun, line 21) — Parses the url and returns a request object that can be further modified or submitted.
- `post` (fun, line 27) — Send a request via POST. All of the params in your requests Uri will be encoded and sent
- `get` (fun, line 32) — Access the contents of a url.
- `delete` (fun, line 35) — Makes a request via DELETE.
- `getCookieManager` (fun, line 37) — entry point other code calls; see callers for context.
- `release` (fun, line 44) — Tell the client it is no longer needed. This will depend on the client but typically
- `setEnabled` (fun, line 51) — Control whether or not new, future network connections are currently allowed.
- `readResponse` (fun, line 62) — Invoked by the client after connecting, provides an InputStream of the response
- `inputStream` (fun, line 67) — entry point other code calls; see callers for context.
- `okioBuffer` (fun, line 68) — entry point other code calls; see callers for context.

## Junior notes

- Read the file top to bottom once; it is small and its declaration order follows its logic.
