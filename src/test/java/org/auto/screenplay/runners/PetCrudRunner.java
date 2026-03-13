package org.auto.screenplay.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "org.auto.screenplay.stepdefinitions")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, io.cucumber.core.plugin.SerenityReporterParallel")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
public class PetCrudRunner {
}

