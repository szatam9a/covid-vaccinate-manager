package covid;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Scanner;

public class ControlMain {
    public static void main(String[] args) throws IOException {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        CitizenRepository citizenRepository = new CitizenRepository(dataSource);
        CitizensService citizensService = new CitizensService(citizenRepository);
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/covid?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Flyway flyway =Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        Scanner sc = new Scanner(System.in);
        System.out.println(Files.readAllLines(Path.of("src/main/resources/menu/menu.txt")));
        System.out.println(citizenRepository.find16CitizenVaccinated15daysAgoOrNon());

    }
}
