[![Build Status](https://api.travis-ci.org/jlawyerorg/j-lawyer-calculations.svg?branch=master)](https://travis-ci.org/jlawyerorg/j-lawyer-calculations)

# j-lawyer-calculations
j-lawyer.org plugin repository. Plugins can be rolled out without updating the j-lawyer.org client or server. 

## How to write a plugin

* Create a file called `<pluginname>_meta.groovy` in directory `src/<j-lawyer-org-version>`, where j-lawyer-org-version is the minimum j-lawyer.org version required for the plugin to run. The file is to describe the plugin in general, e.g. its name and description:
```
name = "RVG-Rechner"
description = "j-lawyer.org RVG-Rechner"
version = "1.0.0";
author = "Jens Kutschke"
updated = "24.12.2018"
supportedPlaceHolders = "{{RVG_TABELLE}}, {{RVG}}"
```
* Create a file called `<pluginname>_ui.groovy` in directory `src/<j-lawyer-org-version>`, where j-lawyer-org-version is the minimum j-lawyer.org version required for the plugin to run. The file will contain both the user interface and logic for your plugin. It might be comprised by additional groovy script files. See some of the existing plugins for how to develope a plugin
* Edit file `j-lawyer-calculations.xml` and add your plugin. 
```xml
<calculation name="pluginname" version="1.0.0" for="1.9.1" url="https://www.j-lawyer.org/downloads/j-lawyer-plugins/calculations/1.9.1" files="pluginname_meta.groovy,pluginname_ui.groovy"/>
<calculation name="rvg" version="1.0.0" for="1.10.0.0" url="https://www.j-lawyer.org/downloads/j-lawyer-plugins/calculations/1.9.1" files="pluginname_meta.groovy,pluginname_ui.groovy"/>
```
You need one entry per j-lawyer.org version, for each of the versions that you would like to provide the plugin for. Attributes: name = Name of the plugin; version = Version of the plugin; for = for which j-lawyer.org version; url = directory where the files are kept. If there are no different versions of the plugin for different j-lawyer.org versions, you can just point to the same / ONE directory. files = list of files that are required for this plugin to run.

* Once you commit and push the changes, the plugin will automatically be uploaded so that all users can use it.

## Documentation

Groovy scripting: http://groovy-lang.org
