package project.cafeweb.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.cafeweb.model.Product;
import project.cafeweb.model.oder_Product;
import project.cafeweb.repository.oder_productRepository;
@Service
public class oder_productService {
	
	  @Autowired
	    private oder_productRepository orderProductRepository;

	    @Autowired
	    private ProductService productService;

	    // Thêm các phương thức xử lý logic đặt hàng
	    public void placeOrder(oder_Product orderProduct) {
	        // Xử lý đặt hàng, ví dụ: kiểm tra số lượng tồn kho, cập nhật cơ sở dữ liệu, v.v.

	        // Tính toán giá và tổng giá tiền trước khi lưu vào cơ sở dữ liệu
	        calculatePriceAndTotal(orderProduct);

	        // Lưu đơn đặt hàng vào cơ sở dữ liệu
	        orderProductRepository.save(orderProduct);
	    }

	    private void calculatePriceAndTotal(oder_Product oder_Product) {
	        // Lấy thông tin về sản phẩm từ cơ sở dữ liệu
	        Product product = productService.getProductById(oder_Product.getProduct().getId());
	        if (product != null) {
	            double Aprice = product.getPrice();
	            int quantity = oder_Product.getQuantityOder();

	            if (quantity > 0) {
	                // Set the calculated total price for the specific product
	                double APriceTotal = Aprice * quantity;
	                
	                // Calculate VAT (5%)
	                double vat = APriceTotal * 0.05;
	                
	                // Set the calculated total price including VAT
	                double totalWithVAT = APriceTotal + vat;
	                
	                oder_Product.setAprice(Aprice);
	                oder_Product.setTotalPrice(totalWithVAT);

	                // Tính tổng tiền của hóa đơn bằng cách cộng dồn total price của từng sản phẩm
	                double totalBill = totalWithVAT;

	                // Set the calculated total bill for the order
	                oder_Product.setTotalPrice(totalBill);
	            } else {
	                // Trường hợp không hợp lệ
	                throw new IllegalArgumentException("Số lượng không hợp lệ");
	            }
	        } else {
	            // Xử lý trường hợp không tìm thấy sản phẩm
	            throw new IllegalArgumentException("Không tìm thấy sản phẩm");
	        }
	    }

		public List<oder_Product> getAllOrders() {
			return orderProductRepository.findAll();
		}

		public oder_Product getOrderById(Long orderId) {
			
			return orderProductRepository.findById(orderId) .orElse(null); 
			}

		public List<oder_Product> listAll() {
			// TODO Auto-generated method stub
			return orderProductRepository.findAll();
		}

		public oder_Product findById(Long orderId) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<oder_Product> getConfirmedOrders() {
			  List<oder_Product> allOrders = orderProductRepository.findAll();
		        return allOrders.stream()
		                .filter(oder_Product::isOrderConfirm)
		                .collect(Collectors.toList());
		}

		public List<oder_Product> getConfirmedOrdersFailded() {
			 List<oder_Product> allOrders = orderProductRepository.findAll();
		        return allOrders.stream()
		                .filter(oder_Product::isCancleOrder)
		                .collect(Collectors.toList());
		}
		
		
		// thống kê theo ngày
		public long countConfirmedOrdersByDate(LocalDate date) {
		    LocalDateTime startDate = date.atStartOfDay();
		    LocalDateTime endDate = date.atTime(23, 59, 59);
		    return orderProductRepository.countConfirmedOrdersByDate(startDate, endDate);
		}

		public long countCanceledOrdersByDate(LocalDate date) {
		    LocalDateTime startDate = date.atStartOfDay();
		    LocalDateTime endDate = date.atTime(23, 59, 59);
		    return orderProductRepository.countCanceledOrdersByDate(startDate, endDate);
		}

		public long countTotalOrdersByDate(LocalDate date) {
		    LocalDateTime startDate = date.atStartOfDay();
		    LocalDateTime endDate = date.atTime(23, 59, 59);
		    return orderProductRepository.countTotalOrdersByDate(startDate, endDate);
		}
		// thống kê theo tuần 
		
		public long countConfirmedOrdersByWeek(LocalDate date) {
		    LocalDateTime startOfWeek = date.atStartOfDay().with(DayOfWeek.MONDAY);
		    LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
		    return orderProductRepository.countConfirmedOrdersByDate(startOfWeek, endOfWeek);
		}

		public long countCanceledOrdersByWeek(LocalDate date) {
		    LocalDateTime startOfWeek = date.atStartOfDay().with(DayOfWeek.MONDAY);
		    LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
		    return orderProductRepository.countCanceledOrdersByDate(startOfWeek, endOfWeek);
		}

		public long countTotalOrdersByWeek(LocalDate date) {
		    LocalDateTime startOfWeek = date.atStartOfDay().with(DayOfWeek.MONDAY);
		    LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
		    return orderProductRepository.countTotalOrdersByDate(startOfWeek, endOfWeek);
		}

		// thống kê theo tháng
		
		public long countConfirmedOrdersByMonth(LocalDate date) {
		    LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
		    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
		    return orderProductRepository.countConfirmedOrdersByDate(startOfMonth, endOfMonth);
		}

		public long countCanceledOrdersByMonth(LocalDate date) {
		    LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
		    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
		    return orderProductRepository.countCanceledOrdersByDate(startOfMonth, endOfMonth);
		}

		public long countTotalOrdersByMonth(LocalDate date) {
		    LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
		    LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
		    return orderProductRepository.countTotalOrdersByDate(startOfMonth, endOfMonth);
		}
		// thống kê theo năm 
		public long countConfirmedOrdersByYear(LocalDate date) {
		    LocalDateTime startOfYear = date.withDayOfYear(1).atStartOfDay();
		    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);
		    return orderProductRepository.countConfirmedOrdersByDate(startOfYear, endOfYear);
		}

		public long countCanceledOrdersByYear(LocalDate date) {
		    LocalDateTime startOfYear = date.withDayOfYear(1).atStartOfDay();
		    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);
		    return orderProductRepository.countCanceledOrdersByDate(startOfYear, endOfYear);
		}

		public long countTotalOrdersByYear(LocalDate date) {
		    LocalDateTime startOfYear = date.withDayOfYear(1).atStartOfDay();
		    LocalDateTime endOfYear = startOfYear.plusYears(1).minusSeconds(1);
		    return orderProductRepository.countTotalOrdersByDate(startOfYear, endOfYear);
		}

		public oder_Product getOrderProductById(Long orderId) {
			  Optional<oder_Product> optionalOrderProduct = orderProductRepository.findById(orderId);

			    return optionalOrderProduct.orElse(null);
		}

		/*
		 * public double calculateTotalPrice(oder_Product orderProduct) { if
		 * (orderProduct != null && orderProduct.getTotalPrice() != null) { return
		 * orderProduct.getTotalPrice(); }
		 * 
		 * return 0.0; // Return 0 if no products or prices are available }
		 */
		
		

		



}
	    
	    
	    

		

