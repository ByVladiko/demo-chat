package com.vldby.demochat.repo;

import com.vldby.demochat.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Set<Role> findByDefaultRoleTrue();
}
