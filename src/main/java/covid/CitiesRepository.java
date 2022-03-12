package covid;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CitiesRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public CitiesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public String findCity(String zipcode) {
        return jdbcTemplate.queryForObject("select name from cities where IRSZ = ?", (rs, rowNum) -> rs.getString("name"),
                Integer.parseInt(zipcode));
    }

}
