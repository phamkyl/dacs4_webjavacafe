package project.cafeweb.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.cafeweb.model.Product;
import project.cafeweb.model.oder_Product;
import project.cafeweb.model.transportProduct;
import project.cafeweb.service.oder_productService;
import project.cafeweb.service.transportService;

@Controller
public class transportProductController {
	
	 @Autowired
	    private transportService transportService;
	 @Autowired
	 private oder_productService oder_productService;
	 
	 @GetMapping("/order-success-list")
	    public String successConfirm(Model model) {
		     
	        List<transportProduct> listtrans = transportService.getListTrans(); // Replace with your actual method
	        model.addAttribute("listtrans", listtrans);
	        return "list_sussess_order_trans"; // Assuming your template file is named "success_cofirm.html"
	    }
	 
	 @GetMapping("/order-Failed-list")
	    public String FailedConfirm(Model model) {
		     
	        List<transportProduct> listtrans = transportService.getListTransFaild(); // Replace with your actual method
	        model.addAttribute("listtrans", listtrans);
	        return "list_faild_trans"; // Assuming your template file is named "success_cofirm.html"
	    }
	 @GetMapping("{orderId}")
	 public String showTransportForm(@PathVariable Long orderId, Model model) {
	     transportProduct transportProduct = new transportProduct();
	     
	     // Assuming orderId is the ID of the associated order
	     Optional<oder_Product> orderProductOptional = Optional.of(oder_productService.getOrderById(orderId));
	     
	     if (orderProductOptional.isPresent()) {
	         oder_Product orderProduct = orderProductOptional.get();
	         transportProduct.setOrderProduct(orderProduct);
	         model.addAttribute("transportProduct", transportProduct);
	         return "FormTransportProduct";
	     } else {
	         // Handle the case where the order is not found
	         // You might want to add proper error handling or validation
	         return "errorPage";
	     }
	     }

	 @PostMapping("/trans-save")
	 public String saveTransportProduct(@ModelAttribute("transportProduct") transportProduct transportProductForm,
	                                    @RequestParam(name = "orderId") Long orderId,
	                                    BindingResult bindingResult, Model model) {
	     // You now have access to orderId, and you can use it as needed...
	     
	     if (bindingResult.hasErrors()) {
	         return "transportProductForm";
	     }

	     // Set the orderProduct using the orderId
	     oder_Product orderProduct = oder_productService.getOrderProductById(orderId);
	     transportProductForm.setOrderProduct(orderProduct);

	     transportProduct transport = transportService.convertToEntity(transportProductForm);
	     transportService.addTransportProduct(transport);

	     model.addAttribute("transportProduct", transport);

	     return "redirect:/confirmed-orders-sussess";
	 }


}
