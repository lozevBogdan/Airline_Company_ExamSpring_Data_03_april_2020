package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.imports.TicketImportDto;
import softuni.exam.models.dto.imports.TicketImportRootDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final String PATH_TICKETS =
            "src/main/resources/files/xml/tickets.xml";

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    public TicketServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, Gson gson,
                             ValidationUtil validationUtil, TownRepository townRepository, XmlParser xmlParser,
                             TicketRepository ticketRepository, PassengerRepository passengerRepository) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count()> 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_TICKETS));
    }

    @Override
    public String importTickets() throws JAXBException {
        StringBuilder sb = new StringBuilder();


        TicketImportRootDto ticketImportRootDto =
                this.xmlParser.parseXml(TicketImportRootDto.class, PATH_TICKETS);

        for (TicketImportDto ticketDto : ticketImportRootDto.getTicketImportDtos()) {

            Optional<Ticket> ticketByName =
                    this.ticketRepository.findBySerialNumber(ticketDto.getSerialNumber());

            Optional<Town> fromTownByName = this.townRepository
                    .findByName(ticketDto.getFromTown().getName());

            Optional<Town> toTownByName = this.townRepository
                    .findByName(ticketDto.getToTown().getName());

            Optional<Passenger> passengerByEmail = this.passengerRepository
                    .findByEmail(ticketDto.getPassenger().getEmail());

            Optional<Plane> planeByRegisteredNumber = this.planeRepository
                    .findByRegisterNumber(ticketDto.getPlane().getRegisterNumber());

            if (this.validationUtil.isValid(ticketDto) && ticketByName.isEmpty() &&
            fromTownByName.isPresent() && toTownByName.isPresent() && passengerByEmail.isPresent()
                    && planeByRegisteredNumber.isPresent()){


                Ticket ticket = this.modelMapper.map(ticketDto, Ticket.class);
                ticket.setTakeoff(ticketDto.getTakeOf());
                ticket.setPassenger(passengerByEmail.get());
                ticket.setFromTown(fromTownByName.get());
                ticket.setToTown(toTownByName.get());
                ticket.setPlane(planeByRegisteredNumber.get());


                this.ticketRepository.save(ticket);

                sb.append(String.format(
                        "Successfully imported Ticket %s - %s%n",ticket.getFromTown().getName(),
                        ticket.getToTown().getName()
                ));



            }else {

                sb.append("Invalid Ticket").append(System.lineSeparator());
            }


        }


        return sb.toString();
    }
}
