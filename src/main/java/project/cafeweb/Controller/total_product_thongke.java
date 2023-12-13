package project.cafeweb.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.cafeweb.model.Product;
import project.cafeweb.service.ProductService;

@Controller
public class total_product_thongke {
	 @Autowired
	    private ProductService productService;

	    @GetMapping("/product-statistics")
	    public String productStatistics(Model model) {
	        List<Product> products = productService.getAllProducts();
	        model.addAttribute("products", products);
	        return "total_product_thongke"; // Assuming you have a Thymeleaf template named product-statistics.html
	    }
}
