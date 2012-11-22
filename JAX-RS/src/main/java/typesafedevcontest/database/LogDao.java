package typesafedevcontest.database;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Date;
import java.util.List;

@Component
public class LogDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public List<Log> findRecent(int limit) {
        return jdbcTemplate.query("select * from logs order by created_at limit ?", new Object[]{limit}, mapper);
    }

    public Log create(final String name, final String line) {

        final Date now = new Date();

        PreparedStatementCreator statementCreator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement statement = conn.prepareStatement(
                        "insert into logs (name, line, created_at, updated_at) values (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setString(2, line);
                statement.setDate(3, new java.sql.Date(now.getTime()));
                statement.setDate(4, new java.sql.Date(now.getTime()));
                return statement;
            }
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(statementCreator, keyHolder);

        Log log = new Log();
        log.setId(keyHolder.getKey().intValue());
        log.setName(name);
        log.setLine(line);
        log.setCreatedAt(now);
        log.setUpdatedAt(now);
        return log;
    }

    RowMapper<Log> mapper = new RowMapper<Log>() {
        public Log mapRow(ResultSet rs, int i) throws SQLException {
            return extractor.extractData(rs);
        }
    };

    ResultSetExtractor<Log> extractor = new ResultSetExtractor<Log>() {
        public Log extractData(ResultSet rs) throws SQLException, DataAccessException {
            Log log = new Log();
            log.setId(rs.getInt("id"));
            log.setName(rs.getString("name"));
            log.setLine(rs.getString("line"));
            log.setCreatedAt(rs.getDate("created_at"));
            log.setUpdatedAt(rs.getDate("updated_at"));
            return log;
        }
    };

}
