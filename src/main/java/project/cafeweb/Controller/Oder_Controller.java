package project.cafeweb.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import project.cafeweb.model.Product;
import project.cafeweb.model.categoryProduct;
import project.cafeweb.model.oder_Product;
import project.cafeweb.model.transportProduct;
import project.cafeweb.service.CategoryProductService;
import project.cafeweb.service.EmailServiceImpl;
import project.cafeweb.service.IEmailService;
import project.cafeweb.service.ProductService;
import project.cafeweb.service.oder_productService;
import project.cafeweb.service.transportService;

@Controller

public class Oder_Controller {

    @Autowired
    private oder_productService orderProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryProductService categoryProductService;

    @Autowired
    private transportService transportProductService;
    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/save-order")
    public String showOrderForm(Model model) {
        model.addAttribute("orderProduct", new oder_Product());
        List<Product> productList = productService.listAll();
        model.addAttribute("products", productList);

        List<categoryProduct> categoryList = categoryProductService.listAll();
        model.addAttribute("categories", categoryList);

        return "order_productForm";
    }

    @PostMapping("/save-order")
    public String placeOrder(@Valid @ModelAttribute("orderProduct") oder_Product orderProduct,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Handle validation errors
            List<Product> productList = productService.listAll();
            model.addAttribute("products", productList);

            List<categoryProduct> categoryList = categoryProductService.listAll();
            model.addAttribute("categories", categoryList);

            return "order_productForm";
        }

        try {
            // Place the order and calculate prices
            orderProductService.placeOrder(orderProduct);

            // Redirect to a success page or show a success message
            return "redirect:/order-list"; // Redirect to a success page
        } catch (Exception e) {
            // Handle exceptions, e.g., product not found, invalid quantity, etc.
            model.addAttribute("error", e.getMessage());

            List<Product> productList = productService.listAll();
            model.addAttribute("products", productList);

            List<categoryProduct> categoryList = categoryProductService.listAll();
            model.addAttribute("categories", categoryList);

            return "order_productForm";
        }
    }

    // hiển thị list order product
    @GetMapping("order-list")
    public String showOrderList(Model model) {
        List<oder_Product> orderList = orderProductService.getAllOrders();
        model.addAttribute("listOrders", orderList);
        return "listOrderProduct";
    }
     // hiển thị danh sách đơn hàng đã xác nhận
    // hiển thị list confirmed orders
    @GetMapping("/confirmed-orders-sussess")
    public String showConfirmedOrders(Model model) {
        List<oder_Product> confirmedOrders = orderProductService.getConfirmedOrders();
        model.addAttribute("listOrders", confirmedOrders);
        return "sussess_cofirm";
    }

// xác nhận đơn hàng
    @GetMapping("/order-list/confirmOrder/{orderId}")
    public String confirmOrder(@PathVariable Long orderId, Model model) {
        try {
            oder_Product order = orderProductService.getOrderById(orderId);

            // Check if the order is already confirmed
            if (order.isOrderConfirm()) {
                model.addAttribute("error", "Order is already confirmed.");
                return "redirect:/order-list";
            }

            // Xác nhận đơn hàng
            order.setOrderConfirm(true);
            orderProductService.placeOrder(order);

            // Trừ số lượng hàng
            productService.updateQuantity(order.getProduct(), order);

            // Gửi email với file PDF đính kèm
            try {
                emailService.sendConfirmationEmail(order);
            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý exception khi gửi email
                model.addAttribute("error", "Failed to send confirmation email.");
                return "redirect:/order-list";
            }

            model.addAttribute("message", "Order confirmed successfully.");
            return "redirect:/order-list";
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý exception khi xác nhận đơn hàng
            model.addAttribute("error", "Failed to confirm the order.");
            return "redirect:/order-list";
        }
    }

    // hủy đơn hàng 
 // hiển thị danh sách đơn hàng đã hủy

    @GetMapping("/confirmed-orders-failed")
    public String showConfirmedOrdersFailed(Model model) {
        List<oder_Product> confirmedOrdersFailed = orderProductService.getConfirmedOrdersFailded();
        model.addAttribute("listOrders", confirmedOrdersFailed);
        return "Failed_Cancel_order";
    }
    @GetMapping("/order-list/cancelOrder/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, Model model) {
        oder_Product order = orderProductService.getOrderById(orderId);

        // Check if the order is already confirmed
        if (order.isOrderConfirm()) {
            model.addAttribute("error", "Cannot cancel a confirmed order.");
            return "redirect:/order-list";
        }

        // Restore product quantity only if the order has not been confirmed
        try {
			/*
			 * productService.restoreQuantity(order.getProduct(), order.getQuantityOder());
			 */
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception when restoring product quantity
            model.addAttribute("error", "Failed to restore product quantity.");
            return "redirect:/order-list";
        }

        // Mark the order as canceled
        order.setCancleOrder(true);
        orderProductService.placeOrder(order);

        // Send cancellation email
        try {
            emailService.sendCancellationEmail(order);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception when sending cancellation email
            model.addAttribute("error", "Failed to send cancellation email.");
        }

        // Return the canceled orders page or redirect to the order list
        return "redirect:/confirmed-orders-failed";
    }



  
   
    
	 
    
   
}
