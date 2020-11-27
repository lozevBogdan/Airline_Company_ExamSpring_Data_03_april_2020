package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.PassengersImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final String PATH_PASSENGERS =
            "src/main/resources/files/json/passengers.json";

    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository, ModelMapper modelMapper,
                                Gson gson, ValidationUtil validationUtil, TownRepository townRepository) {
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_PASSENGERS));
    }

    @Override
    public String importPassengers() throws IOException {

        StringBuilder sb = new StringBuilder();

        PassengersImportDto[] passengersImportDtos = this.gson.fromJson(this.readPassengersFileContent(),
                PassengersImportDto[].class);


        for (PassengersImportDto passenegrDto : passengersImportDtos) {
            Optional<Town> townByName = this.townRepository.findByName(passenegrDto.getTown());
            Optional<Passenger> byEmail =
                    this.passengerRepository.findByEmail(passenegrDto.getEmail());


            if (this.validationUtil.isValid(passenegrDto) && byEmail.isEmpty() &&
                    townByName.isPresent()){

                Passenger passenger = this.modelMapper.map(passenegrDto, Passenger.class);

                passenger.setTown(townByName.get());
                this.passengerRepository.save(passenger);

                sb.append(String.format("Successfully imported Passenger %s - %s%n"
                ,passenger.getLastName(),passenger.getEmail()));


            }else {

                sb.append("Invalid Passenger").append(System.lineSeparator());
            }

        }


        return sb.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
StringBuilder sb = new StringBuilder();

        List<Passenger> passengers =
                this.passengerRepository.passengerOrderByTicketsCountAndEmail();


        for (Passenger passenger : passengers) {

            sb.append(String.format("Passenger %s  %s\n" +
                    "\tEmail - %s\n" +
                    "\tPhone - %s\n" +
                    "\tNumber of tickets - %d\n",
                    passenger.getFirstName(),passenger.getLastName(),passenger.getEmail(),
                    passenger.getPhoneNumber(),passenger.getTickets().size()));
            sb.append(System.lineSeparator());
        }



        return sb.toString();
    }
}
