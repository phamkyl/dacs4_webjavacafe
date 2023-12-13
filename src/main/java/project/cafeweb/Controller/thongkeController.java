package project.cafeweb.Controller;

import java.time.DayOfWeek;
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

import net.bytebuddy.asm.Advice.Return;
import project.cafeweb.service.oder_productService;

@Controller
public class thongkeController {
	 @Autowired
	    private oder_productService orderProductService;

	 
	 // thống kê đơn hàng đã xác nhận , hủy xác nhận , tổng đơn hàng  ( bao gồm xác nhận và hủy)
	 @GetMapping("/order-statistics")
	 public String orderStatistics(
	         @RequestParam(name = "date", required = false)
	         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	         Model model) {
	     
	     if (date == null) {
	         // Nếu không có ngày được chỉ định, sử dụng ngày hiện tại
	         date = LocalDate.now();
	     }

	     // Thống kê theo ngày
	     long confirmedOrders = orderProductService.countConfirmedOrdersByDate(date);
	     long canceledOrders = orderProductService.countCanceledOrdersByDate(date);
	     long totalOrders = orderProductService.countTotalOrdersByDate(date);

	     model.addAttribute("confirmedOrders", confirmedOrders);
	     model.addAttribute("canceledOrders", canceledOrders);
	     model.addAttribute("totalOrders", totalOrders);

	     // Thống kê cho tháng
	     LocalDate firstDayOfMonth = date.withDayOfMonth(1);
	     long confirmedOrdersMonth = orderProductService.countConfirmedOrdersByMonth(firstDayOfMonth);
	     long canceledOrdersMonth = orderProductService.countCanceledOrdersByMonth(firstDayOfMonth);
	     long totalOrdersMonth = orderProductService.countTotalOrdersByMonth(firstDayOfMonth);

	     model.addAttribute("confirmedOrdersMonth", confirmedOrdersMonth);
	     model.addAttribute("canceledOrdersMonth", canceledOrdersMonth);
	     model.addAttribute("totalOrdersMonth", totalOrdersMonth);

	     // Thống kê cho tuần
	     LocalDate firstDayOfWeek = date.with(DayOfWeek.MONDAY);
	     long confirmedOrdersWeek = orderProductService.countConfirmedOrdersByWeek(firstDayOfWeek);
	     long canceledOrdersWeek = orderProductService.countCanceledOrdersByWeek(firstDayOfWeek);
	     long totalOrdersWeek = orderProductService.countCanceledOrdersByWeek(firstDayOfWeek);

	     model.addAttribute("confirmedOrdersWeek", confirmedOrdersWeek);
	     model.addAttribute("canceledOrdersWeek", canceledOrdersWeek);
	     model.addAttribute("totalOrdersWeek", totalOrdersWeek);

	     // Thống kê cho năm
	     LocalDate firstDayOfYear = date.withDayOfYear(1);
	     long confirmedOrdersYear = orderProductService.countConfirmedOrdersByYear(firstDayOfYear);
	     long canceledOrdersYear = orderProductService.countCanceledOrdersByYear(firstDayOfYear);
	     long totalOrdersYear = orderProductService.countTotalOrdersByYear(firstDayOfYear);

	     model.addAttribute("confirmedOrdersYear", confirmedOrdersYear);
	     model.addAttribute("canceledOrdersYear", canceledOrdersYear);
	     model.addAttribute("totalOrdersYear", totalOrdersYear);

	     // Dữ liệu cho biểu đồ ngày
	     List<Long> dailyData = new ArrayList<>();
	     dailyData.add(orderProductService.countConfirmedOrdersByDate(date));
	     dailyData.add(orderProductService.countCanceledOrdersByDate(date));
	     dailyData.add(orderProductService.countTotalOrdersByDate(date));

	     model.addAttribute("dailyData", dailyData);
	     
	  // Dữ liệu cho biểu đồ tuần
	     List<Long> weekyData = new ArrayList<>();
	     weekyData.add(orderProductService.countConfirmedOrdersByWeek(firstDayOfWeek));
	     weekyData.add(orderProductService.countCanceledOrdersByWeek(firstDayOfWeek));
	     weekyData.add(orderProductService.countCanceledOrdersByWeek(firstDayOfWeek));

	     model.addAttribute("weekyData", weekyData);
	     
	     // dữ liệu cho biểu đồ theo tháng 
	     List<Long> MonthData = new ArrayList<>();
	     MonthData.add(orderProductService.countConfirmedOrdersByMonth(firstDayOfMonth));
	     MonthData.add(orderProductService.countCanceledOrdersByMonth(firstDayOfMonth));
	     MonthData.add(orderProductService.countCanceledOrdersByMonth(firstDayOfMonth));

	     model.addAttribute("MonthData", MonthData);
	     
	     // dữ liệu cho biểu đồ theo năm
	     List<Long> YearData = new ArrayList<>();
	     YearData.add(orderProductService.countConfirmedOrdersByYear(firstDayOfYear));
	     YearData.add(orderProductService.countCanceledOrdersByYear(firstDayOfYear));
	     YearData.add(orderProductService.countCanceledOrdersByYear(firstDayOfYear));

	     model.addAttribute("YearData", YearData);
	     

	     return "thongke";
	 }
	 
	 // thống kê đơn hàng thành công, đơn hàng thất bại , tổng doanh thu, 
	 // số lượng  sản phẩm đã bán , số lượng sản phẩm còn tồn kho
	  

}
