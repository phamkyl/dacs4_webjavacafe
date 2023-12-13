package project.cafeweb.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import project.cafeweb.model.Product;
import project.cafeweb.model.categoryProduct;

import project.cafeweb.model.dto.UserRegistrationDto;
import project.cafeweb.repository.CategoryProductRepository;
import project.cafeweb.service.CategoryProductService;
import project.cafeweb.service.ProductService;
import project.cafeweb.service.UserService;

@Controller
 @RequestMapping
public class categoryController {
	   @Autowired
	    private CategoryProductService categoryProductService;

	    @GetMapping("/category-save")
	    public String addProductType(categoryProduct producttype, Model model){
	        model.addAttribute("producttype", producttype);
	        return "FormcategoryProduct";
	    }

	
		  @GetMapping("/edit/{id}") public String editProductType(@PathVariable("id")
		  int id, Model model){ categoryProduct producttype =
		  categoryProductService.get(id);
		  
		  model.addAttribute("producttype", producttype);
		
		  return "FormcategoryProduct"; }
	

	    @PostMapping("/category-save")
	    public String saveProductType(@Valid @ModelAttribute("producttype") categoryProduct producttype, BindingResult result, Model model)
	    {
	        model.addAttribute("producttype", producttype);
	
	        if(result.hasErrors()){
	            return "FormcategoryProduct";
	        }
	        categoryProductService.save(producttype);

	        return "FormcategoryProduct";
	    }
		
	    @GetMapping("/delete/{id}")
	    public String deleteProductType(@PathVariable("id") int id, Model model) {
	        categoryProductService.delete(id);

	        return "listcategoryProduct";
	    }
		  
		  @GetMapping("/category-list") public String listProductType(Model model) {
		  List<categoryProduct> listProductTypes = categoryProductService.listAll();
		  model.addAttribute("listProductTypes", listProductTypes); 
		  return "listcategoryProduct"; 
		  }
		 
	}
	