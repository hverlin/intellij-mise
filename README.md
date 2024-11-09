![Build](https://github.com/134130/intellij-mise/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/24904-mise.svg)](https://plugins.jetbrains.com/plugin/24904-mise)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/24904-mise.svg)](https://plugins.jetbrains.com/plugin/24904-mise)

<!-- Plugin description -->
# Mise

**[GitHub](https://github.com/134130/intellij-mise)** | **[Issue](https://github.com/134130/intellij-mise/issues)** | **[Changelog](https://github.com/134130/intellij-mise/blob/main/CHANGELOG.md)**

**Mise** is a plugin for JetBrains IDEs that allows you to set environment variables for your run configurations
from Mise config files. see: **[mise-en-place](https://mise.jdx.dev)**

### Supported Platforms
- **IntelliJ IDEA**
- **GoLand**
- **WebStorm**
- **Rider**
- _Submit issue if you need other IDE_

### Features

- **Environment Variables**: Set environment variables for your run configurations from Mise config files.
- **Tool Integration**: Set project's SDK automatically from Mise config files.

### `mise ls` supports

- `java`
- `go`
- `node`
- `deno`

### `mise set` supports

- IntelliJ IDEA
- GoLand
- WebStorm
- Rider

<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Mise"</kbd> >
  <kbd>Install</kbd>
  
- Manually:

  Download the [latest release](https://github.com/134130/intellij-mise/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
