# Sonar Klingon Plugin Guide

This guide aims to add to the study of the code itself by explaining the different components used in it.

The plugin itself is an example on how to create a plugin for SonarQube that analyzes an arbitrary language (Klingon), either by performing the parsing of files within itself or importing an XML report from another tool.

[The official documentation for plugin development](https://docs.sonarqube.org/latest/extend/developing-plugin/) serves as an introduction, while this plugin was modified from [SonarQube Custom Plugin Example](https://github.com/SonarSource/sonar-custom-plugin-example) by agigleux.

## POM

Sonar recommends using Maven as the dependency manager and building tool for a project, though Gradle instructions are in the official documentation.

I have yet to define where to declare imported libraries that will be used in the plugin, or if the code must be copied into the source itself.

## Plugin entry point

Every Sonar plugin must have a class that defines its entry point. This is the `KlingonPlugin` class that implements `org.sonar.api.Plugin`. Its role is to define the other classes taht form the plugin, Sonar takes care of initializing them at different stages.

## Rules definition

`KlingonRulesDefinition` implements `org.sonar.api.server.rule.RulesDefinition`. It defines one or various repositories that contain rules for a given language.

There are multiple ways of assigning a rule to a repository. The original example defined them by declaring the rules in a single XML file. This example uses `org.sonarsource.analyzer.commons.RuleMetadataLoader` (imported via the `sonar-analyzer-commons` dependency in the `pom.xml` file) to register rules by iterating over a provided list of classes annotated with `@Rule` (helpfully provided via the `CheckList` class) and loading their metadata from single html and json files placed in a given folder. This is what happens for each class:

1. A class is annotated with `@Rule(key = abc123)`

2. The rule key is added to the repository

3. The contents of the file `abc123.html` are associated with the rule (this serves as the description of the rule in the SonarQube server in a pretty format, better than using the rule annotation parameters)

4. The contents of the file `abc123.json` are associated with the rule (this defines the type of rule, the severity, etc. It could be done using the annotation parameters, but this defines more fields that would have to be configured manually in the server otherwise)

5. The rule is activated by default

This just takes care of registering the rule in Sonar. It is the Sensor's job to run checks using rule classes and saving issues using the rule key.

## Language and language properties

For a sensor to know which files to analyze, a language must define the file extensions it uses. This is done extending `org.sonar.api.resources.AbstractLanguage` (which `Klingon` class does in the example) and overriding the method `getFileSuffixes()`. The example also uses the class `KlingonLanguageProperties` to define the suffixes and to register the properties in the server. I've yet to determine the usefulness registering the properties, my belief is so that the file extensions can be manually configured in the server.

This also registers the language itself so it can be recognized by the server and assigned to a repository as seen in the rules definition.

## Quality Profile

In Sonar, a Quality Profile is a set of rules for a given language. Only one Quality Profile can be active for one language per project. We can define one or various Quality Profiles for our plugin with certain rules activated by default.

To define a built in Quality Profile a class must implement `org.sonar.api.server.profile.BuiltInQualityProfilesDefinition`.

The original example did this by assigning rules one by one (and overriding severity if needed).

This example uses `org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader` to read from a json file simply containing the keys for the rules to activate:

```json
{
  "name": "Klingon Lint",
  "ruleKeys": [
    "K001",
    "K002"
  ]
}
```

The name of the Quality Profile is the one defined in the class, not the json file.

## Custom sensor

All the previous classes have all the configuration needed to integrate the plugin in the server, and not all are mandatory. The sensor is the main component of the plugin.

A sensor must implement `org.sonar.api.batch.sensor.Sensor`. It is tasked with filtering files to analyze, running checks on them and saving the issues that are reported during the analysis.

It can do this in two ways:

- Performing the analysis itself by filtering files through the project's filesystem with the language extensions and running checks on said files. This is why it is useful to get the file suffixes from the overridden method in the class implementing `AbstractLanguage` and getting the same list of checks provided to the class implementing `RulesDefinition`.

- Parsing a report form a different tool, unrelated to Sonar, given in an XML format and generating issues from it.
