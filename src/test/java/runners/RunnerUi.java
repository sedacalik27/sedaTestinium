package runners;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ui.BeymenTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                BeymenTest.class
        }
)
public class RunnerUi {

}
