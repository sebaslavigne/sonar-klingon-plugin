/*
 * Example Plugin for SonarQube
 * Copyright (C) 2009-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sebas.plugins.klingon;

import org.sebas.plugins.klingon.settings.Klingon;
import org.sebas.plugins.klingon.settings.KlingonQualityProfile;
import org.sebas.plugins.klingon.settings.KlingonRulesDefinition;
import org.sebas.plugins.klingon.settings.KlingonLintIssuesLoaderSensor;
import org.sebas.plugins.klingon.settings.KlingonLanguageProperties;
import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;

import static java.util.Arrays.asList;
import static org.sebas.plugins.klingon.settings.KlingonLanguageProperties.FILE_SUFFIXES_DEFAULT_VALUE;

/**
 * This class is the entry point for all extensions. It is referenced in pom.xml.
 */
public class KlingonPlugin implements Plugin {
	
	@Override
	public void define(Context context) {
		// tutorial on hooks
		// http://docs.sonarqube.org/display/DEV/Adding+Hooks
//    context.addExtensions(DisplayIssuesInScanner.class, DisplayQualityGateStatus.class);
		
		// tutorial on languages
		context.addExtensions(Klingon.class, KlingonQualityProfile.class);
		context.addExtensions(KlingonLanguageProperties.getProperties());
		
		// tutorial on measures
//    context
//      .addExtensions(ExampleMetrics.class, SetSizeOnFilesSensor.class, ComputeSizeAverage.class, ComputeSizeRating.class);
		
		// tutorial on rules
//    context.addExtensions(JavaRulesDefinition.class, CreateIssuesOnJavaFilesSensor.class);
		context.addExtensions(KlingonRulesDefinition.class, KlingonLintIssuesLoaderSensor.class);
		
		// tutorial on settings
//    context
//      .addExtensions(HelloWorldProperties.getProperties())
//      .addExtension(SayHelloFromScanner.class);
		
		// tutorial on web extensions
//    context.addExtension(MyPluginPageDefinition.class);
		
//		context.addExtensions(asList(
//			PropertyDefinition.builder("sonar.klingon.file.suffixes")
//				.name("Suffixes Klingon")
//				.description("Suffixes supported by Klingon")
//				.category("Klingon")
//				.defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
//				.build()));
	}
}
