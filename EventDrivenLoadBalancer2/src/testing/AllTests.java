package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import testing.IDGenerator.TestIDGenerationService;
import testing.IDGenerator.TestIDGenerator;

@RunWith(Suite.class)
@SuiteClasses({
	TestIDGenerator.class,
	TestIDGenerationService.class
})
public class AllTests {

}
