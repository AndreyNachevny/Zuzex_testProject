package andreynachevny.testTask.repositories;

import andreynachevny.testTask.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {
    Optional<House> findByAddress(String name);
    Optional<House> findByIdOwner(int id);
}
