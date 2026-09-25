# Pocket/src/main/assets/html/c/text-tablet.css

## What this is

This stylesheet overrides article typography and layout for tablets. It sets looser line heights, larger top and bottom margins on `#RIL_container` (the readability article wrapper), centered login prompts, and sized article headers, lists, rules, and code blocks.

## How it fits

Loaded by `article-mobile-tablet.html` and `article-mobile-smalltablet.html` right after `c/text.css`, so its rules take precedence over the phone base styles. Side margins not set here are applied dynamically from JavaScript in `j/articleview-mobile.js` after page load. Entries: body and `#RIL_container` spacing, `.large_font_size` heading caps, `ul`/`ol`/`hr`/`pre` treatment, `.login_prompt` centering, `#RIL_header` title and byline sizing.
