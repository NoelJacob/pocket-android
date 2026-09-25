# .idea/codeStyles/Project.xml

## What this is
This is the shared IntelliJ/Android Studio code-style scheme: the formatting rules (indentation, import order, wrapping, trailing commas) the whole team edits against. It covers Java, Kotlin (official Kotlin style with trailing commas allowed), Groovy, HTML, XML layouts, JSON, and YAML. The `README.md` points developers here as the default, with "be reasonable and follow the codebase" as the tiebreaker.

## How it fits
Android Studio applies this scheme automatically when you reformat code, and reviewers assume diffs conform to it — mismatched import ordering is the most common visible violation. The notable values: wildcard imports only after 99 names (effectively never), `WRAP_COMMENTS`, trailing commas kept in JSON and Kotlin, and the `IMPORT_LAYOUT_TABLE` ordering for Java/Kotlin imports.
