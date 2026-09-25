# gradle/wrapper/gradle-wrapper.jar

## What this is
This is the Gradle wrapper bootstrap binary: a small committed jar that downloads and launches the exact Gradle distribution declared in `gradle-wrapper.properties` (currently 8.14.3). It is what makes `./gradlew` work on a machine with only a JVM installed — no system Gradle needed. It is a build artifact, not source; never run or inspect its contents directly.

## How it fits
Both `./gradlew` (POSIX) and `gradlew.bat` (Windows) are thin scripts whose only real job is `java -jar gradle/wrapper/gradle-wrapper.jar` with your arguments forwarded. CI (GitHub workflows, Bitrise) and every developer build go through it, so the whole repo builds with one Gradle version. Upgrading means changing the distribution URL in the properties file, not replacing this jar.
