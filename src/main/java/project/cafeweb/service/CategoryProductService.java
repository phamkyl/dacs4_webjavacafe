package project.cafeweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import project.cafeweb.model.categoryProduct;
import project.cafeweb.repository.CategoryProductRepository;

@Service
public class CategoryProductService {
	
	 
	   @Autowired
	   private  CategoryProductRepository repo;

	    public List<categoryProduct> listAll() {
	        return (List<categoryProduct>) repo.findAll();
	    }

	    public void save(categoryProduct product) {
	        repo.save(product);
	    }

	    public categoryProduct get(long  id) {
	        return repo.findById(id).get();
	    }

	    public void delete(long id) {
	        repo.deleteById(id);
	    }

}
