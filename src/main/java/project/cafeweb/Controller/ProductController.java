package project.cafeweb.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import project.cafeweb.model.Product;
import project.cafeweb.model.categoryProduct;
import project.cafeweb.model.oder_Product;
import project.cafeweb.service.CategoryProductService;
import project.cafeweb.service.ProductService;

@Controller

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryProductService categoryProductService;
    
    @GetMapping("/product-save")
    
    public String getFormProduct(Model model) {
        List<categoryProduct> productTypes = categoryProductService.listAll();
        model.addAttribute("productTypes", productTypes);
        
        model.addAttribute("product", new Product()); // Thêm sản phẩm mới để binding với form

        return "FormProduct";
    }
    
    
    @GetMapping("/add-product")
    public String addProduct(Product product, Model model) {
        model.addAttribute("product", product);
        List<categoryProduct> productTypes = categoryProductService.listAll();
        model.addAttribute("productTypes", productTypes);

        return "FormProduct";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable("id") long id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);

        List<categoryProduct> productTypes = categoryProductService.listAll();
        model.addAttribute("productTypes", productTypes);

        return "edit-FormProduct";
    }

    @PostMapping("/product-save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              @RequestParam("imageFile") MultipartFile imageFile,
                              BindingResult result, Model model) {
        List<categoryProduct> productTypes = categoryProductService.listAll();
        model.addAttribute("productTypes", productTypes);

        if (result.hasErrors()) {
            return "FormProduct";
        }

        try {
            // Kiểm tra xem người dùng đã chọn tệp hình ảnh chưa
            if (!imageFile.isEmpty()) {
                // Lưu trữ hình ảnh dưới dạng mảng byte
                byte[] imageBytes = imageFile.getBytes();
                product.setImage(imageBytes);
            }

            // Lưu sản phẩm vào cơ sở dữ liệu
            productService.save(product);

            return "redirect:/list-products";
        } catch (IOException e) {
            e.printStackTrace();
            result.rejectValue("image", "error.product", "Error while saving image");
            return "FormProduct";
        }
    }



    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        productService.delete(id);
        return "redirect:/list-products";
    }

    @GetMapping("/list-products")
    public String listProduct(Model model) {
        List<categoryProduct> productTypes = categoryProductService.listAll();
        model.addAttribute("productTypes", productTypes);

        List<Product> listProducts = productService.listAll();
        model.addAttribute("listProducts", listProducts);

        return "listProduct";
    }
}
