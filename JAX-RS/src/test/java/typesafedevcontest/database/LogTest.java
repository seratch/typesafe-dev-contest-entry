package typesafedevcontest.database;

import typesafedevcontest.BasicSpringTestCase;
import org.junit.Test;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class LogTest extends BasicSpringTestCase {

    @Resource
    Log log;

    @Test
    public void instantiation() throws Exception {
        assertThat(log, notNullValue());
    }

}
