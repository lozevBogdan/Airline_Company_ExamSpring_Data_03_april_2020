package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.TownImportDto;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {

    private final String PATH_TOWNS = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TOWNS));
    }

    @Override
    public String importTowns() throws IOException {

        StringBuilder sb = new StringBuilder();

        TownImportDto[] townImportDtos =
                this.gson.fromJson(this.readTownsFileContent(), TownImportDto[].class);

        for (TownImportDto townDto : townImportDtos) {

            Optional<Town> byName = this.townRepository.findByName(townDto.getName());

            if(this.validationUtil.isValid(townDto) && byName.isEmpty()){

                Town town = this.modelMapper.map(townDto, Town.class);
                this.townRepository.save(town);
                sb.append(String.format("Successfully imported Town %s - %d%n"
                , town.getName(),town.getPopulation()));

            }else {

                sb.append("Invalid Town").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
