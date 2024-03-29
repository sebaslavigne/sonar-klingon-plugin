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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionXmlLoader;
import org.sonarsource.analyzer.commons.RuleMetadataLoader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class KlingonRulesDefinition implements RulesDefinition {
	
	public static final String PATH_TO_RULES_METADATA = "klingon/rules";
	
	protected static final String KEY = "analyzer";
	protected static final String NAME = "Sonar Analyzer";
	
	public static final String REPO_KEY = Klingon.KEY + "-" + KEY;
	protected static final String REPO_NAME = Klingon.KEY + "-" + NAME;
	
	protected String rulesDefinitionFilePath() {
		return PATH_TO_RULES_METADATA;
	}
	
	private void defineRulesForLanguage(Context context, String repositoryKey, String repositoryName, String languageKey) {
		NewRepository repository = context.createRepository(repositoryKey, languageKey).setName(repositoryName);
		
		InputStream rulesXml = this.getClass().getResourceAsStream(rulesDefinitionFilePath());
		if (rulesXml != null) {
			RulesDefinitionXmlLoader rulesLoader = new RulesDefinitionXmlLoader();
			rulesLoader.load(repository, rulesXml, StandardCharsets.UTF_8.name());
		}
		
		repository.done();
	}
	
	@Override
	public void define(Context context) {
//    defineRulesForLanguage(context, REPO_KEY, REPO_NAME, Klingon.KEY);
		NewRepository repository = context.createRepository(REPO_KEY, Klingon.KEY).setName(REPO_NAME);
		RuleMetadataLoader ruleMetadataLoader = new RuleMetadataLoader(PATH_TO_RULES_METADATA);
		
		ruleMetadataLoader.addRulesByAnnotatedClass(repository, CheckList.getKlingonClasses());
		repository.done();
	}
	
	
}
