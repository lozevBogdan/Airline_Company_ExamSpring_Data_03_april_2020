package softuni.exam.models.dto.imports;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportRootDto {

    @XmlElement(name = "ticket")
    private List<TicketImportDto> ticketImportDtos;

    public TicketImportRootDto() {
    }

    public List<TicketImportDto> getTicketImportDtos() {
        return ticketImportDtos;
    }

    public void setTicketImportDtos(List<TicketImportDto> ticketImportDtos) {
        this.ticketImportDtos = ticketImportDtos;
    }
}
