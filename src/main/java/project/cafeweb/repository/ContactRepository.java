package project.cafeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.cafeweb.model.contact;

@Repository
public interface ContactRepository extends JpaRepository<contact, Long> {
    // Add custom query methods if needed
}
