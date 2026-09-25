# Pocket/src/main/java/com/pocket/util/java/IntUtils.java
## What this is
A one-method helper, `compare(x, y)`, returning -1, 0, or 1 for two ints. It predates `Integer.compare()` (added in Java 7) and keeps old call sites compiling on older toolchains.
For example, `IntUtils.compare(a.getPriority(), b.getPriority())` orders two tasks without writing the ternary by hand.

## How it fits
Sits in the generic `com.pocket.util.java` toolbox with no Pocket-specific dependencies. It is a leaf helper any comparator-style code can call; the modern equivalent is `Integer.compare()`, so new code should prefer the standard library.

## Key pieces
- `compare(int x, int y)`: sign of `x - y` computed without overflow-prone subtraction. WHY it exists: a null-free, allocation-free ordering primitive from before the JDK provided one.

## Junior notes
- Never implement int comparison as `return x - y`; it overflows for large values. The ternary here (and `Integer.compare`) avoids that.
- For new code, use `Integer.compare(x, y)` from the standard library instead of this class.
