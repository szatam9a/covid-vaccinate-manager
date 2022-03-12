package covid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.*;

public class CitizenRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public CitizenRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void saveCitizen(String name, int zipCode, int age, String emailAddress, int TAJ) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into citizens(citizen_name,zip,age,email,taj,number_of_vaccination,last_vaccination) " +
                "values(?,?,?,?,?) ", name, zipCode, age, emailAddress, TAJ);
    }

    public void saveCitizen(Citizen citizen) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into citizens(citizen_name,zip,age,email,taj,number_of_vaccination,last_vaccination) " +
                "values(?,?,?,?,?) ", citizen.getName(), citizen.getZipCode(), citizen.getAge(), citizen.getEmailAddress(), citizen.getTAJ());
    }

    public long saveCitizenWithID(String name, String zipCode, int age, String emailAddress, int TAJ) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "insert into citizens(citizen_name,zip,age,email,taj,number_of_vaccination,last_vaccination)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, zipCode);
            ps.setInt(3, age);
            ps.setString(4, emailAddress);
            ps.setInt(5, TAJ);
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

    public long saveCitizenWithID(Citizen citizen) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "insert into citizens(citizen_name,zip,age,email,taj,number_of_vaccination,last_vaccination)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, citizen.getName());
            ps.setString(2, citizen.getZipCode());
            ps.setInt(3, citizen.getAge());
            ps.setString(4, citizen.getEmailAddress());
            ps.setInt(5, citizen.getTAJ());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }


    public Map<LocalTime, Citizen> find16CitizenVaccinated15daysAgoOrNon() {
        Map<LocalTime, Citizen> result = new TreeMap<>();
        List<Citizen> citizens = new LinkedList<>();
        citizens = jdbcTemplate.query("select * from citizens WHERE (number_of_vaccination = 0 or NULL) OR (DATEDIFF(NOW(),citizens.last_vaccination)<15)",
                //String name, String zipCode, int age, String emailAddress, int TAJ
                (rs, rowNum) -> new Citizen(
                        rs.getLong("citizen_id"),
                        rs.getString("citizen_name"),
                        rs.getString("zip"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getInt("taj")
                ));
        Collections.sort(citizens, Comparator.comparing(Citizen::getAge).reversed());
        System.out.println(citizens);
        int time = 8 * 60;
        for (Citizen c : citizens) {
            result.put(LocalTime.of(time / 60, time % 60), c);
            time += 30;
        }
        return result;
    }
}

