# .idea/externalDependencies.xml

## What this is
This tiny file pins one required IDE plugin: the GraphQL plugin (`com.intellij.lang.jsgraphql`). When you open the project, Android Studio notices the pin and offers to install the plugin if it is missing. That is the whole file — one component, one plugin, no settings.

## How it fits
The repo's sync engine is defined in `.graphqls` schema files and `.graphql` queries (`sync-pocket/src/main/graphql/`, `figment.graphql`), and the GraphQL plugin is what gives those files syntax highlighting, validation, and navigation inside the IDE. Without it you can still build (codegen runs in Gradle), but editing schemas becomes plain-text guessing.
