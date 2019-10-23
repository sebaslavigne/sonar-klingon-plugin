package org.sebas.plugins.klingon.settings;

import org.sebas.plugins.klingon.checks.GruntCheck;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.rule.RuleKey;

public class KlingonSensor implements Sensor {
	
	private final Checks<Object> checks;
	private final FileLinesContextFactory contextFactory;
	
	public KlingonSensor(CheckFactory checkFactory, FileLinesContextFactory fileLinesContextFactory) {
		checks = checkFactory.create(KlingonRulesDefinition.REPOSITORY).addAnnotatedChecks((Iterable) CheckList.getKlingonClasses());
		this.contextFactory = fileLinesContextFactory;
	}
	
	@Override
	public void describe(SensorDescriptor descriptor) {
		descriptor.name("Klingon sensor");
		descriptor.onlyOnLanguage(Klingon.KEY);
	}
	
	@Override
	public void execute(SensorContext context) {
		
		FileSystem fileSystem = context.fileSystem();
		
		FilePredicates predicates = fileSystem.predicates();
		Iterable<InputFile> inputFiles = fileSystem.inputFiles(
				predicates.and(
						predicates.hasType(InputFile.Type.MAIN),
						predicates.hasLanguages(Klingon.KEY)
				));
		
		for (InputFile file : inputFiles) {
			FileLinesContext fileLinesContext = contextFactory.createFor(file);
			fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, 1, 1);
			fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, 2, 1);
			fileLinesContext.save();
			
			context.<Integer>newMeasure()
					.withValue(2)
					.forMetric(CoreMetrics.NCLOC)
					.on(file)
					.save();
			
			NewIssue newIssue = context.newIssue()
					.forRule(RuleKey.of(KlingonRulesDefinition.REPOSITORY, GruntCheck.RULE_KEY));
			NewIssueLocation location = newIssue.newLocation()
					.message("Testing grunt issue for every file")
					.on(file);
			newIssue.at(location);
			newIssue.save();
		}
	}
}
