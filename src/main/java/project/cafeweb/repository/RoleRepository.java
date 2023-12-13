package project.cafeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.cafeweb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
