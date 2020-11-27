package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;

import java.util.Optional;

@Repository
public interface PlaneRepository  extends JpaRepository<Plane,Integer> {

    Optional<Plane> findByRegisterNumber(String registerNumber);

}
