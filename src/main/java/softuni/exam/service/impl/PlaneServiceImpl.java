package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.PlaneImportDto;
import softuni.exam.models.dto.imports.PlaneImportRootDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService {

    private final String PATH_PLANES =
            "src/main/resources/files/xml/planes.xml";

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper,
                            Gson gson, ValidationUtil validationUtil, TownRepository townRepository, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PATH_PLANES));
    }

    @Override
    public String importPlanes() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        PlaneImportRootDto planeImportRootDto =
                this.xmlParser.parseXml(PlaneImportRootDto.class, PATH_PLANES);

        for (PlaneImportDto planeImportDto : planeImportRootDto.getPlaneImportDtos()) {

            Optional<Plane> planeByRegNumber =
            this.planeRepository.findByRegisterNumber(planeImportDto.getRegisterNumber());

                    if(this.validationUtil.isValid(planeImportDto) && planeByRegNumber.isEmpty()){

                        Plane plane = this.modelMapper.map(planeImportDto, Plane.class);

                        this.planeRepository.save(plane);

                        sb.append(String.format("Successfully imported Plane %s%n",
                                plane.getRegisterNumber()));

                    }else {

                        sb.append("Invalid Plane").append(System.lineSeparator());
                    }
        }

        return sb.toString();
    }
}
