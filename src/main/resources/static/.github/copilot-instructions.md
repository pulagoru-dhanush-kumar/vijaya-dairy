<!-- Copilot / AI agent instructions for this small frontend repo -->
# AI Coding Assistant Instructions

Purpose: Quickly orient an AI coding agent to be productive in this repository (minimal static frontend).

- **Project snapshot:** This repo is a tiny static frontend with three top-level files: `index.html`, `script.js`, and `style.css`.
- **No build system:** There is no `package.json`, bundler, or test harness present. Changes are applied directly to the three files.

Key files
- `index.html`: entry HTML. Keep structural markup and external script/style includes here.
- `script.js`: single JS entry for DOM behavior. Prefer adding functions and minimal global state.
- `style.css`: site styles. Use class-based selectors; avoid embedding critical styling in `index.html`.

Architecture & patterns (discoverable)
- Single-page static site: user-facing UI is defined in `index.html` and enhanced by `script.js`.
- Separation of concerns: HTML for structure, CSS for presentation, JS for behavior. Follow that split when adding features.
- No frameworks or modules detected: keep code plain ES5/ES6 compatible, avoid assuming a bundler.

Developer workflows (what an agent should do)
- Local preview: there is no build — serve the folder with a static server. Example commands an engineer might use:
  - `python -m http.server 8000` (Python 3)
  - `npx http-server -c-1` (if Node available)
- Edits: update the three files directly. Keep markup minimal and avoid introducing complex tool-specific code (e.g., Webpack config) unless explicitly requested.

Conventions & expectations
- Global scope: `script.js` currently acts as the global entry — prefer a single namespaced object or IIFE to avoid globals.
- DOM hooks: reference elements by `id` when a single instance is required, and by `data-*` attributes for reusable components.
- CSS: prefer classes (no deep selector chains). Keep responsive rules simple and local to the component where possible.

Integration points & external deps
- No external dependencies are committed. If adding a third-party script or CSS, update `index.html` and document the reason in the PR.

How to propose changes (for AI-generated PRs)
- Make a single, focused change per PR (e.g., add a feature or fix a bug) that edits only the files necessary.
- In the PR description, include: purpose, files changed, and manual test steps (how to verify in a browser).

Examples (concrete guidance)
- To add a click handler that toggles a panel:
  - Put markup in `index.html` with `id="panelToggle"` and `id="panel"`.
  - Add minimal JS in `script.js` using `document.getElementById('panelToggle')` and `classList.toggle()`.
  - Add CSS `.hidden { display: none; }` in `style.css`.

What NOT to assume
- There is no CI, package manager, or preconfigured linter. Do not add tooling without the maintainer's approval.

If anything is unclear or you need additional context (e.g., intended UX, target browsers, or planned packaging), ask the maintainer before adding build tooling or large refactors.

---
If you'd like, I can also add a tiny README with run instructions and an example PR template. Confirm and I'll add it.
