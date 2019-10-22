package org.sebas.plugins.klingon.settings;

import org.junit.Test;
import org.sonar.api.config.internal.MapSettings;

import static org.assertj.core.api.Assertions.assertThat;

public class KlingonTest {
	
	@Test
	public void testDefaultFileSuffixes() {
		MapSettings settings = new MapSettings();
		settings.setProperty(Klingon.FILE_SUFFIXES_KEY, Klingon.FILE_SUFFIXES_DEFAULT_VALUE);
		Klingon klingon = new Klingon(settings.asConfig());
		assertThat(klingon.getFileSuffixes()).containsOnly(".klg", ".kgn");
	}
}
