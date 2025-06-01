package runners;

import api.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                C01PostBoard.class,
                C02PostList.class,
                C03PostCard.class,
                C04PutCard.class,
                C05DeleteCard.class,
                C06DeleteBoard.class
        }
)
public class RunnerApi {



}
