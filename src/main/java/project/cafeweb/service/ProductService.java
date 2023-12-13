package project.cafeweb.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import project.cafeweb.model.Product;
import project.cafeweb.model.oder_Product;
import project.cafeweb.repository.ProductRepository;

@Service
public class ProductService {
	   @Autowired
	    private ProductRepository repo;

	    public List<Product> listAll() {
	        return repo.findAll();
	    }
	    
	    public Product getProductById(Long productId) {
	        return repo.findById(productId).orElse(null);
	    }

	    public Page<Product> findPaginated(String search, Pageable pageable) {
	        List<Product> products = repo.searchProduct(search); //repo.findAll();

	        int pageSize = pageable.getPageSize();
	        int currentPage = pageable.getPageNumber();
	        int startItem = currentPage * pageSize;
	        List<Product> list;

	        if (products.size() < startItem) {
	            list = Collections.emptyList();
	        } else {
	            int toIndex = Math.min(startItem + pageSize, products.size());
	            list = products.subList(startItem, toIndex);
	        }

	        Page<Product>CafePage = new PageImpl<Product>(list, PageRequest.of(currentPage, pageSize), products.size());

	        return CafePage;
	    }


	    public void save(Product product) {
	        repo.save(product);
	    }

	    public Product get(long id) {
	        return repo.findById(id).get();
	    }

	    public void delete(long id) {
	        repo.deleteById(id);
	    }

	    
		
		  public void updateQuantity(Product product, oder_Product oder_Product) {
		  
		  int currentQuantity = product.getQuantity(); 
		  int currentInventoryNumber =product.getBefore_quantity();
		  int quantityToBuy = oder_Product.getQuantityOder();
		  
		 if (currentQuantity >= quantityToBuy) { 
			 int remainingQuantity =
		  currentQuantity - quantityToBuy;
		  
		  if (remainingQuantity >= 0) { 
		product.setQuantity(currentQuantity);
		  product.setBefore_quantity(remainingQuantity); // Update Inventory_numberbased on remaining quantity 
		  save(product);
		  } else 
		  { throw new
		 RuntimeException("Not enough quantity in stock."); } } else { throw new
		 RuntimeException("Product quantity is not sufficient for the order."); } }
		 

		
		/*
		 * public void updateQuantity(Product product) {
		 * 
		 * int currentQuantity = product.getQuantity(); if (currentQuantity > 0) {
		 * product.setQuantity(currentQuantity - 1); save(product); } else { throw new
		 * RuntimeException("Product quantity is not sufficient."); }
		 * 
		 * 
		 * }
		 */
		 

		public void saveOrUpdate(Product product) {
			 repo.save(product);
			
		}
		
		public void restoreQuantity(Product product, int quantityToRestore) throws Exception {
		    // Add logic to restore the product quantity to its initial state
		    int currentQuantity = product.getBefore_quantity();
		   
		    product.setBefore_quantity(currentQuantity + quantityToRestore);
		    saveOrUpdate(product);
		}

		public List<Product> getAllProducts() {
			// TODO Auto-generated method stub
			return repo.findAll();
		}


}
