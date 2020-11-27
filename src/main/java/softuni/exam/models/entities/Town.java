package softuni.exam.models.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "towns")
public class Town extends BaseEntity {


    private String name;
    private int population;
    private String guide;
    private List<Passenger> passengers;
    private List<Ticket> toTown;
    private List<Ticket> fromTown;


    public Town() {
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Column
    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @OneToMany(mappedBy = "town")
    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @OneToMany(mappedBy = "toTown")
    public List<Ticket> getToTown() {
        return toTown;
    }

    public void setToTown(List<Ticket> toTown) {
        this.toTown = toTown;
    }
    @OneToMany(mappedBy = "fromTown")
    public List<Ticket> getFromTown() {
        return fromTown;
    }

    public void setFromTown(List<Ticket> fromTown) {
        this.fromTown = fromTown;
    }
}
