package org.sebas.plugins.klingon.settings;

import org.sebas.plugins.klingon.checks.GruntCheck;
import org.sebas.plugins.klingon.checks.HonorCheck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckList {
	
	public static List<Class> getKlingonClasses() {
		return Collections.unmodifiableList(Arrays.asList(
				HonorCheck.class,
				GruntCheck.class
		));
	}
}
