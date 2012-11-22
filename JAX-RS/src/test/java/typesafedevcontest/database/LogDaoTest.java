package typesafedevcontest.database;

import org.junit.Test;
import typesafedevcontest.RollbackTestCase;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LogDaoTest extends RollbackTestCase {

    @Resource
    LogDao dao;

    @Test
    public void create() throws Exception {
        Log created = dao.create("access_log", "xxxyyyy");
        assertThat(created.getId(), is(notNullValue()   ));
    }

}
