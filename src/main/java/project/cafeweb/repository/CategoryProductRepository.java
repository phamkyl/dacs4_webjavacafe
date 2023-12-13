package project.cafeweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.cafeweb.model.categoryProduct;

public interface CategoryProductRepository extends JpaRepository<categoryProduct, Long> {


	
}
