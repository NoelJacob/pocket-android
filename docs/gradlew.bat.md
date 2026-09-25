# gradlew.bat

## What this is
This is the Windows counterpart to `gradlew`: a batch script that runs the Gradle wrapper on `cmd.exe`. It locates `java.exe` (via `JAVA_HOME` or `PATH`), sets minimal default JVM options, and launches the same committed `gradle/wrapper/gradle-wrapper.jar` with your arguments forwarded. Same pinned Gradle 8.14.3, same builds — just Windows syntax.

## How it fits
Windows developers and Windows CI agents use `gradlew.bat :Pocket:lint` (or any other task) exactly where POSIX users type `./gradlew`. Everything downstream — repository order from root `build.gradle.kts`, versions from the catalog, modules from `settings.gradle.kts` — is identical because both scripts converge on the same wrapper jar and properties file.

## Key pieces
- **`findJavaFromJavaHome` / PATH fallback** — exists to give a clear error ("set JAVA_HOME…") instead of a cryptic launch failure when Java is missing.
- **`DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"`** — intentionally small wrapper-only heap; the real build heap comes from `gradle.properties` once Gradle itself starts.
- **`-jar "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" %*`** — the handoff line: all arguments pass through to the wrapper untouched.

## Junior notes
- Keep this file's line endings as CRLF (Windows style); converting to LF can break `cmd.exe` parsing.
- `GRADLE_EXIT_CONSOLE` quirk at the bottom only matters when embedding the script in other batch files — ignore it for normal builds.
