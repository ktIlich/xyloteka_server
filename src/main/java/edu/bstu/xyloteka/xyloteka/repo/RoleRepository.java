package edu.bstu.xyloteka.xyloteka.repo;


import edu.bstu.xyloteka.xyloteka.models.Role;
import edu.bstu.xyloteka.xyloteka.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}
