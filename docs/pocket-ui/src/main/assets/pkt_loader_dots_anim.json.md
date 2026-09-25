# pocket-ui/src/main/assets/pkt_loader_dots_anim.json

## What this is

A small Lottie loading animation (Bodymovin 5.5.1 export, 90×90, ~30fps, frames 22–72) of Pocket-teal dots, used as an indeterminate progress indicator. Like its feed-footer sibling it is vector keyframes, not video, so it stays crisp at any size.

## How it fits

Played by Lottie-backed loading views in `pocket-ui` wherever content is fetched (list refresh, reader load) instead of the stock Android spinner, keeping loading states on-brand. Swap or retime this file to change every dots-loader in the app at once.
