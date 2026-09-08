// Minimal local backend for Pocket developDebug e2e (Express).
// Wire shapes proven by sync-pocket unit tests (PocketRemoteSourceShould) and V3Source docs.
// Run: node --watch server.js   (listens 0.0.0.0:8080)
const express = require('express');

const WIKI_URL = 'https://en.wikipedia.org/wiki/Pocket_(service)';
const WIKI_TITLE = 'Pocket (service) - Wikipedia';

const app = express();
app.use(express.json({ limit: '5mb' }));
app.use(express.urlencoded({ extended: true }));

// Request log: the protocol oracle. METHOD path + body keys + raw query.
app.use((req, res, next) => {
  const keys = req.body && typeof req.body === 'object' ? Object.keys(req.body) : [];
  console.log(`${req.method} ${req.path}${keys.length ? ' keys=' + keys.join(',') : ''}`);
  next();
});

const X_SOURCE = { 'X-Source': 'Pocket' };

// Proven by PocketRemoteSourceShould.kt:37-44.
app.all('/v3/guid', (req, res) => {
  res.set(X_SOURCE).json({ guid: 'local-test-guid' });
});

app.all('/v3/send_guid', (req, res) => {
  res.set(X_SOURCE).json({ action_results: [true] });
});

// Save path: ItemRepository.save posts an `add` action; answer per-action results.
app.all('/v3/send', (req, res) => {
  let n = 1;
  try {
    const a = req.body && (req.body.actions || req.body.action);
    const arr = typeof a === 'string' ? JSON.parse(a) : a;
    if (Array.isArray(arr)) n = arr.length;
  } catch (e) { /* default single true */ }
  res.set(X_SOURCE).json({ action_results: new Array(n).fill(true) });
});

// Login round-trip: loginWithAccessToken posts here; answer with a minimal
// account so PocketCache persists LoginInfo (isLoggedIn = access_token != null).
app.all('/v3/getAfterLogin', (req, res) => {
  res.set(X_SOURCE).json({
    account: {
      user_id: '1',
      username: 'local',
      email: 'local@example.com',
      premium_status: true,
    },
    prompt_password: false,
    premium_gift: {},
  });
});


// List read-back: minimal v3/get shape with the single Wikipedia item.
app.all(/^\/v3\/get.*/, (req, res) => {
  res.set(X_SOURCE).json({
    status: 1,
    list: {
      1: {
        item_id: '1',
        resolved_id: '1',
        given_url: WIKI_URL,
        resolved_url: WIKI_URL,
        resolved_title: WIKI_TITLE,
        favorite: '0',
        status: '0',
        is_article: 1,
        has_video: 0,
        is_index: 0,
      },
    },
  });
});

// Parser: pre-parsed article HTML for the Wikipedia URL (no client parser exists;
// jsoup in-app is TTS/image-matching only). ArticleView fields per generated class.
const ARTICLE_PARAS = ["Pocket, formerly known as Read It Later, was a social bookmarking service for storing, sharing and discovering web bookmarks, first released in 2007. Mozilla, the developer of Pocket, announced in May 2025 that it was discontinuing the service and would shut it down in July of that year.", "Pocket was introduced in August 2007 as a Mozilla Firefox browser extension named Read It Later by Nathan (Nate) Weiner. Once his product was used by millions of people, he moved his office to Silicon Valley and four other people joined the Read It Later team. Weiner's intention was for the application to be like a TiVo directory for web content and to give users access to that content on any device.", "Read It Later obtained venture capital investments of US$2.5 million in 2011 and $5.0 million in 2012. The 2011 funding came from Foundation Capital, Baseline Ventures, Google Ventures, Founder Collective and unnamed angel investors. The company rejected an acquisition offer by Evernote after showing concerns that Evernote intended to shut down the Read It Later service and amalgamate its functionality into Evernote's main service.", "Initially, the Read It Later app was available in a free version and a paid version that included additional features. After the rebranding to Pocket, all paid features were made available in a free and advertisement-free app. In May 2014, a paid subscription service called Pocket Premium was introduced, adding server-side storage of articles and more powerful search tools.", "In June 2015, Pocket was included in Firefox, via a toolbar button and link to a user's Pocket list in the bookmark's menu. The integration was controversial, as users displayed concerns for the direct integration of a proprietary service into an open source application, and that it could not be completely disabled without editing advanced settings, unlike other third-party extensions. A Mozilla spokesperson stated that the feature was meant to leverage the service's popularity among Firefox users and clarified that all code related to the integration was open source. The spokesperson added that \"[Mozilla had] gotten lots of positive feedback about the integration from users\".", "On February 27, 2017, Pocket announced that it had been acquired by Mozilla Corporation, the commercial arm of Firefox's non-profit development group. Mozilla staff stated that Pocket would continue to operate as an independent subsidiary but that it would be leveraged as part of an ongoing \"Context Graph\" project. There were plans to open-source the server-side code of Pocket, though only parts of the project had been open-sourced as of 2024.", "On May 22, 2025, Mozilla announced that it would shut down Pocket on July 8, 2025. Exports of user data would be available until October 8, 2025, when accounts would be deleted. The email newsletter Pocket Hits was rebranded as Ten Tabs on June 12 as part of the closure, with it being changed to release only on weekdays.", "The application allows the user to save an article or web page to remote servers for later reading. The article is sent to the user's Pocket list (synced to all of their devices) for offline reading. Pocket makes the article more readable by removing clutter and enabling the user to add tags and adjust text settings.", "The application had 17 million users and 1 billion saves, as of September 2015. Pocket was listed among Time magazine's 50 Best Android Applications for 2013.", "Kent German of CNET said that \"Read It Later is oh so incredibly useful for saving all the articles and news stories I find while commuting or waiting in line.\" Erez Zukerman of PC World said that supporting the developer is enough reason to buy what he deemed a \"handy app\". Bill Barol of Forbes said that although Read It Later works less well than Instapaper, \"it makes my beloved Instapaper look and feel a little stodgy.\""];
app.all('/parser', (req, res) => {
  const markup = ARTICLE_PARAS.map((p) => `<p>${p}</p>`).join('\n');
  res.set(X_SOURCE).json({
    article: markup,
    resources: [],
    item: { given_url: WIKI_URL, time_added: '1700000000', idkey: '1' },
  });
});

// List fetch: Fetch thing shape (V3 style). This feeds My List / read-back.
app.all('/v3/fetch', (req, res) => {
  res.set(X_SOURCE).json({
    status: 1,
    total: 1,
    remaining_items: 1,
    remaining_chunks: 0,
    list: [
      {
        item_id: '1',
        resolved_id: '1',
        given_url: WIKI_URL,
        resolved_url: WIKI_URL,
        resolved_title: WIKI_TITLE,
        favorite: '0',
        status: '0',
        is_article: 1,
        has_video: 0,
        is_index: 0,
        time_added: '1700000000',
        time_updated: '1700000000',
      },
    ],
  });
});

// GraphQL endpoint present so ClientApi traffic is observable, not fatal.
app.all('/graphql', (req, res) => {
  res.set(X_SOURCE).json({ data: {} });
});

// Catch-all: log loudly, return empty object so shapes surface in app logs.
app.all(/.*/, (req, res) => {
  console.log(`UNHANDLED ${req.method} ${req.path} body=${JSON.stringify(req.body).slice(0, 300)}`);
  res.set(X_SOURCE).json({});
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, '0.0.0.0', () => {
  console.log(`pocket-local-server on 0.0.0.0:${PORT}`);
});
