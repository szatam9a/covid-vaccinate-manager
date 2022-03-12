package covid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CitizensService {
    private CitizenRepository citizenRepository;

    public CitizensService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    public void saveFromFile(Path path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
            lines.remove(0);
            for (String line : lines) {
                citizenRepository.saveCitizen(makeAnIndividual(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public Citizen(String name, String zipCode, int age, String emailAddress, int TAJ) {
    //                Név;Irányítószám;Életkor;E-mail cím;TAJ szám
    private Citizen makeAnIndividual(String line) {
        String[] tmp = line.split(";");
        return new Citizen(tmp[0], tmp[1], Integer.parseInt(tmp[2]), tmp[0], Integer.parseInt(tmp[0]));
    }
}
