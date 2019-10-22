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
package org.sebas.plugins.klingon.settings;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.api.resources.Qualifiers;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * This class defines the fictive Foo language.
 */
public final class Klingon extends AbstractLanguage {
	
	public static final String NAME = "Klingon";
	public static final String KEY = "klingon";
	public static final String FILE_SUFFIXES_KEY = "sonar.klingon.file.suffixes";
	public static final String FILE_SUFFIXES_DEFAULT_VALUE = ".klg,.kgn";
	
	private final Configuration config;
	
	public Klingon(Configuration config) {
		super(KEY, NAME);
		this.config = config;
	}
	
	public static List<PropertyDefinition> getProperties() {
		return asList(PropertyDefinition.builder(FILE_SUFFIXES_KEY)
				.defaultValue(FILE_SUFFIXES_DEFAULT_VALUE)
				.category("Klingon")
				.name("Klingon File Suffixes")
				.description("List of file suffixes that will be scanned.")
				.category("Klingon")
				.onQualifiers(Qualifiers.PROJECT)
				.multiValues(true)
				.build());
		
	}
	
	@Override
	public String[] getFileSuffixes() {
		String[] suffixes = filterEmptyStrings(config.getStringArray(FILE_SUFFIXES_KEY));
		if (suffixes.length == 0) {
			suffixes = StringUtils.split(FILE_SUFFIXES_DEFAULT_VALUE, ",");
		}
		return suffixes;
	}
	
	private String[] filterEmptyStrings(String[] stringArray) {
		List<String> nonEmptyStrings = new ArrayList<>();
		for (String string : stringArray) {
			if (StringUtils.isNotBlank(string.trim())) {
				nonEmptyStrings.add(string.trim());
			}
		}
		return nonEmptyStrings.toArray(new String[nonEmptyStrings.size()]);
	}
	
}
