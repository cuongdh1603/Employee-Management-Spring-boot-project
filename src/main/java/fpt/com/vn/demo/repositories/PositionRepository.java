package fpt.com.vn.demo.repositories;

import fpt.com.vn.demo.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position,Integer> {
    Position findPositionByName(@Param("name") String name);
}
