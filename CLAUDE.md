# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the **j-lawyer-calculations** plugin repository for [j-lawyer.org](https://www.j-lawyer.org), a German legal case management system. Plugins are Groovy scripts that provide calculation tools (fee calculators, table lookups, invoice utilities) to the j-lawyer.org client. Plugins are distributed dynamically via download — no client/server update required.

## Build & Deploy

- **Build:** `ant clean build` (NetBeans Ant project, targets Java 8)
- **Deploy:** `ant upload` — uploads plugin files via SCP to www.j-lawyer.org. Requires env vars: `UPLOADUSER`, `UPLOADPWD`, `UPLOADMETAXML`
- **CI/CD:** GitHub Actions runs `ant upload` on push to `master` — committing to master automatically publishes plugins to all users

## Architecture

### Plugin Structure

Each plugin consists of two or more Groovy scripts in `src/<version-dir>/`:

- **`<name>_meta.groovy`** — metadata: `name`, `description`, `version`, `author`, `updated`, `supportedPlaceHolders`
- **`<name>_ui.groovy`** — UI (Groovy SwingBuilder) and business logic
- Optional helper classes (e.g., `InvoiceUtils.groovy`, `RvgTablesRange.groovy`)

Plugins use `org.jlawyer.plugins.calculation.*` APIs from the j-lawyer.org runtime.

### Source Directory Versions

Source directories (`src/2.2.0.0/`, `src/2.3.0.0/`, `src/2.6.0.0/`) represent minimum j-lawyer.org versions required. Most active plugins live in `src/2.3.0.0/`. If a plugin works across versions, all versions can point to the same source directory.

### Plugin Registry

`src/j-lawyer-calculations.xml` is the master registry. Each plugin needs one `<calculation>` entry per supported j-lawyer.org version, specifying: `name`, `version`, `for` (target j-lawyer version), `url` (download directory), and `files` (required script files).

When adding a new j-lawyer.org version: duplicate the latest version block, update the `for` attribute, and adjust any version-specific URLs or files.

### Adding a New Plugin

1. Create `<name>_meta.groovy` and `<name>_ui.groovy` in the appropriate `src/<version>/` directory
2. Add `<calculation>` entries in `src/j-lawyer-calculations.xml` for each supported j-lawyer.org version
3. If the plugin needs new files uploaded, add the source directory to the `upload` target in `build.xml`

## Key Conventions

- All UI code uses German locale (`Locale.GERMANY`) for number/currency formatting
- Plugins can import from other plugin scripts (e.g., `import rvgtables_ui`)
- All source files carry GNU AGPL v3 license headers (template in `nbproject/licenseheader.txt`)
- Plugin versions are independent from j-lawyer.org versions
- Date format in metadata: `dd.MM.yyyy`
