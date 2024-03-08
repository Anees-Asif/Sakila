package com.example.sakila.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.example.sakila.tests", // should be the package containing your step definitions
        plugin = {"pretty", "html:target/cucumber"}
)
public class RunCucumberTest {
    // No methods needed, this is just a configuration class
}
