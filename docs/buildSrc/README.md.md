# buildSrc/README.md

## What this is

This is the module readme: the starting guide for this directory, explaining what lives here and how to work with it.

## How it fits

Read it before touching buildSrc convention plugins or version catalogs; it points at the files that own each build decision.

## Key pieces


Concrete endpoints referenced here:

- `https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)`

## Junior notes

- BuildSrc code runs at Gradle configuration time: a mistake here breaks every module's build, so change it carefully and sync after editing.
