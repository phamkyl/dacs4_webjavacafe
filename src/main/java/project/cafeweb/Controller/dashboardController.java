package project.cafeweb.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.cafeweb.service.oder_productService;
import project.cafeweb.service.transportService;

@Controller
public class dashboardController {
	
	
	
	
	 @Autowired
	    private oder_productService orderProductService;
	    @Autowired
	    private transportService transportService;
	  
	    @GetMapping("/dashboard")
		public String home(
				   @RequestParam(name = "date", required = false)
			         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
			         Model model) {
	    	
	    	 if (date == null) {
		         // Nếu không có ngày được chỉ định, sử dụng ngày hiện tại
		         date = LocalDate.now();
		     }
	    	 // tổng đơn hàng đặt , hủy, xác nhận trong ngày
	    	 long confirmedOrders = orderProductService.countConfirmedOrdersByDate(date);
		     long canceledOrders = orderProductService.countCanceledOrdersByDate(date);
		     long totalOrders = orderProductService.countTotalOrdersByDate(date);
		     model.addAttribute("confirmedOrders", confirmedOrders);
		     model.addAttribute("canceledOrders", canceledOrders);
		     model.addAttribute("totalOrders", totalOrders);
		     // số đơn thành công , thất bại
		  // Thống kê số đơn hàng thành công trong ngày
		        long successfulOrders = transportService.countSuccessfulOrdersByDate(date);
		        model.addAttribute("successfulOrders", successfulOrders);

		        // Thống kê số đơn hàng thất bại trong ngày
		        long failedOrders = transportService.countFailedOrdersByDate(date);
		        model.addAttribute("failedOrders", failedOrders);
		        // thống kê tổng đơn hàng thành công và thất bại
		        long totalSuccessfulOrders = transportService.getTotalSuccessfulOrders();
		        long totalFailedOrders = transportService.getTotalFailedOrders();

		        model.addAttribute("totalSuccessfulOrders", totalSuccessfulOrders);
		        model.addAttribute("totalFailedOrders", totalFailedOrders);
		        //tổng doanh thu
				/*
				 * double totalRevenue =
				 * transportService.calculateTotalRevenueFromSuccessfulOrders();
				 * model.addAttribute("totalRevenue", totalRevenue);
				 */
		        
		        // biểu đồ số đơn hàng thành công, thất bại trong ngày
		        long successfulOrderschart = transportService.countSuccessfulOrdersByDate(date);
		        long failedOrderschart = transportService.countFailedOrdersByDate(date);

		        // Add these values to the model for Thymeleaf to use
		        model.addAttribute("successfulOrderschart", successfulOrderschart);
		        model.addAttribute("failedOrderschart", failedOrderschart);
		        
		        // biểu đồ lượng đơn trong ngày ,xác nhận , hủy
		        // Dữ liệu cho biểu đồ ngày
			     List<Long> dailyData = new ArrayList<>();
			     dailyData.add(orderProductService.countConfirmedOrdersByDate(date));
			     dailyData.add(orderProductService.countCanceledOrdersByDate(date));
			     dailyData.add(orderProductService.countTotalOrdersByDate(date));

			     model.addAttribute("dailyData", dailyData);
                   

			return "dasboard";
		}
}
