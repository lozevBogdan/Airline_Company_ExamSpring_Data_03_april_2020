package softuni.exam.models.entities;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "planes")
public class Plane extends BaseEntity {

    private String	registerNumber;
    private int capacity;
    private String airline;
    private List<Ticket> tickets;


    public Plane() {
    }

    @Column(unique = true)
    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    @Column
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    @Column
    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    @OneToMany(mappedBy = "plane",fetch = FetchType.EAGER)
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
