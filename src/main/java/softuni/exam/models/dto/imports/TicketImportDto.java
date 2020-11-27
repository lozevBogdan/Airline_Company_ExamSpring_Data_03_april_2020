package softuni.exam.models.dto.imports;


import org.hibernate.validator.constraints.Length;
import softuni.exam.config.LocalDateTimeAdapter;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeOf;
    @XmlElement(name = "from-town")
    private TownImportTicketDto fromTown;
    @XmlElement(name = "to-town")
    private TownImportTicketDto toTown;
    @XmlElement
    private PassengerImportTicketDto passenger;
    @XmlElement
    private PlaneImportTicketDto plane;


    public TicketImportDto() {
    }

    @Length(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    @Min(value = 0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeOf() {
        return takeOf;
    }

    public void setTakeOf(LocalDateTime takeOf) {
        this.takeOf = takeOf;
    }

    public TownImportTicketDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownImportTicketDto fromTown) {
        this.fromTown = fromTown;
    }

    public TownImportTicketDto getToTown() {
        return toTown;
    }

    public void setToTown(TownImportTicketDto toTown) {
        this.toTown = toTown;
    }

    public PassengerImportTicketDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerImportTicketDto passenger) {
        this.passenger = passenger;
    }

    public PlaneImportTicketDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneImportTicketDto plane) {
        this.plane = plane;
    }
}
