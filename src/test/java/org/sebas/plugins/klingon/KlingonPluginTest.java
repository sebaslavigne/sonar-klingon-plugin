package org.sebas.plugins.klingon;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import static org.assertj.core.api.Assertions.assertThat;

public class KlingonPluginTest {
	
	public static final int PLUGIN_EXTENSIONS_AMOUNT = 6;
	
	@Test
	public void klingonPluginTester() {
		Plugin.Context context = new Plugin.Context(SonarRuntimeImpl.forSonarQube(
				Version.create(7, 9),
				SonarQubeSide.SERVER,
				SonarEdition.COMMUNITY));
		
		new KlingonPlugin().define(context);
		
		assertThat(context.getExtensions()).hasSize(PLUGIN_EXTENSIONS_AMOUNT);
	}
}
