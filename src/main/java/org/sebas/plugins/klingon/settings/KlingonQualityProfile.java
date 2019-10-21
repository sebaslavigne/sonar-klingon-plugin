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

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.sonarsource.analyzer.commons.BuiltInQualityProfileJsonLoader;

/**
 * Default, BuiltIn Quality Profile for the projects having files of the language "foo"
 */
public final class KlingonQualityProfile implements BuiltInQualityProfilesDefinition {
  
  public static final String QUALITY_PROFILE_NAME = "KlingonLint Rules";
  public static final String QUALITY_PROFILE_PATH = "klingon/rules/klingonlint_profile.json";
  
  @Override
  public void define(Context context) {
    NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(QUALITY_PROFILE_NAME, Klingon.KEY);
    profile.setDefault(true);
    BuiltInQualityProfileJsonLoader.load(profile, KlingonRulesDefinition.REPO_KEY, QUALITY_PROFILE_PATH);
    profile.done();

//    NewBuiltInActiveRule rule1 = profile.activateRule(KlingonRulesDefinition.REPO_KEY, "ExampleRule1");
//    rule1.overrideSeverity("BLOCKER");
//    NewBuiltInActiveRule rule2 = profile.activateRule(KlingonRulesDefinition.REPO_KEY, "ExampleRule2");
//    rule2.overrideSeverity("MAJOR");
//    NewBuiltInActiveRule rule3 = profile.activateRule(KlingonRulesDefinition.REPO_KEY, "ExampleRule3");
//    rule3.overrideSeverity("CRITICAL");
    
  }

}
