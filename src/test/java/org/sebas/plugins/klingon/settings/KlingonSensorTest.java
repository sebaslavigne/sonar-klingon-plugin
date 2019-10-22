package org.sebas.plugins.klingon.settings;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.DefaultActiveRules;
import org.sonar.api.batch.rule.internal.NewActiveRule;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.server.rule.RulesDefinition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class KlingonSensorTest {
	
	private static final Path TEST_FILES_PATH = Paths.get("src/test/resources/klingon-files");
	
	private KlingonSensor sensor;
	private SensorContextTester tester;
	
	@Before
	public void setUp() {
		KlingonRulesDefinition klingonRulesDefinition = new KlingonRulesDefinition();
		RulesDefinition.Context context = new RulesDefinition.Context();
		klingonRulesDefinition.define(context);
		RulesDefinition.Repository repository = context.repository(KlingonRulesDefinition.REPOSITORY);
		
		List<NewActiveRule> ar = new ArrayList<>();
		for (RulesDefinition.Rule rule : repository.rules()) {
			ar.add(new NewActiveRule.Builder().setRuleKey(RuleKey.of(KlingonRulesDefinition.REPOSITORY, rule.key())).build());
		}
		
		ActiveRules activeRules = new DefaultActiveRules(ar);
		
		CheckFactory checkFactory = new CheckFactory(activeRules);
		FileLinesContextFactory contextFactory = mock(FileLinesContextFactory.class);
		when(contextFactory.createFor(Mockito.any(InputFile.class))).thenReturn(mock(FileLinesContext.class));
		
		sensor = new KlingonSensor(checkFactory, contextFactory);
		tester = SensorContextTester.create(TEST_FILES_PATH);
	}
	
	@Test
	public void testSensor() throws IOException {
		DefaultInputFile inputFile = createInputFile(TEST_FILES_PATH, "test1.klg");
		tester.fileSystem().add(inputFile);
		
		sensor.execute(tester);
		
		assertThat(tester.allIssues()).hasSize(1);
	}
	
	private DefaultInputFile createInputFile(Path dir, String fileName) throws IOException {
		return new TestInputFileBuilder("key", fileName)
				.setModuleBaseDir(dir)
				.setLanguage(Klingon.KEY)
				.setType(InputFile.Type.MAIN)
				.initMetadata(new String(Files.readAllBytes(dir.resolve(fileName)), StandardCharsets.UTF_8))
				.setCharset(StandardCharsets.UTF_8)
				.build();
	}
}
