package org.isbel8ai.training.clinic.repository;

import org.isbel8ai.training.clinic.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
