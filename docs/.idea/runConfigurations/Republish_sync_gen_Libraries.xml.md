# .idea/runConfigurations/Republish_sync_gen_Libraries.xml

## What this is
This is a shared one-click Gradle run configuration called "Republish sync-gen Libraries". It runs four `sync-gen` tasks in order: clean, publish the Pocket model jar, publish the sync-tests jar, and regenerate the examples. It exists because editing the sync code generator requires republishing its output jars before downstream modules (and the IDE itself) see the new generated code.

## How it fits
After changing anything under `sync-gen/src` (generators) or the GraphQL schemas, a developer runs this configuration from Android Studio's run dropdown instead of remembering the four task names. Downstream, `sync-pocket` and `sync-pocket-android` consume the republished jars, so this is the bridge step between "edited the generator" and "app code sees new models". The task names it runs are `:sync-gen:clean`, `:sync-gen:pocketGenJarPublish`, `:sync-gen:syncTestsGenJarPublish`, and `:sync-gen:generateExamples`.
