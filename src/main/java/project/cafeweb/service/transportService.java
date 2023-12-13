package project.cafeweb.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.cafeweb.model.oder_Product;
import project.cafeweb.model.transportProduct;
import project.cafeweb.repository.TransportRepository;
import project.cafeweb.repository.oder_productRepository;

@Service
public class transportService {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private oder_productRepository orderProductRepository;
    @Autowired
    private oder_productService oder_productService;

    public void addTransportProduct(transportProduct transportProductForm) {
        // Convert TransportProductForm to TransportProduct entity if needed
        transportProduct transportProduct = convertToEntity(transportProductForm);

        // Perform any business logic/validation if needed

        // Save the transport product
        transportRepository.save(transportProduct);
    }

    public transportProduct convertToEntity(transportProduct transportProductForm) {
        transportProduct transportProduct = new transportProduct();

        transportProduct.setDateTransport(transportProductForm.getDateTransport());
        transportProduct.setReason(transportProductForm.getReason());
        transportProduct.setSuccessProductDate(transportProductForm.getSuccessProductDate());

     // Assuming you have an OrderProduct entity and a method to get it by ID
        oder_Product orderProduct = transportProductForm.getOrderProduct();

        if (orderProduct != null) {
            // Assuming you have an OrderProduct entity and a method to get it by ID
            oder_Product retrievedOrderProduct = orderProductRepository.findById(orderProduct.getId()).orElse(null);

            if (retrievedOrderProduct != null) {
                transportProduct.setOrderProduct(retrievedOrderProduct);
            } else {
                // Handle the case where the order is not found
                // You might want to add proper error handling or validation
            }
        } else {
            // Handle the case where getOrderProduct() returns null
            // You might want to add proper error handling or validation
        }

        // Set other properties as needed
        transportProduct.setCreationDate(transportProductForm.getCreationDate());
        transportProduct.setOrderStatus(transportProductForm.isOrderStatus()); // Set the boolean value
        transportProduct.setOrderProduct(orderProduct);
        return transportProduct;
    }

	public List<transportProduct> getListTrans() {
		 List<transportProduct> allOrders = transportRepository.findAll();
	        return allOrders.stream()
	                .filter(transportProduct::isOrderStatus)
	                .collect(Collectors.toList());
	}
	


	public List<transportProduct> getListTransFaild() {
		 List<transportProduct> allOrders = transportRepository.findAll();
	        return allOrders.stream()
	                .filter(transport -> !transport.isOrderStatus())
	                .collect(Collectors.toList());
	}
	
	// thống kê số lượng đơn thành công , thất bại
	// Thống kê số đơn hàng thành công trong ngày
    public long countSuccessfulOrdersByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return transportRepository.countSuccessfulOrdersByDate(startOfDay, endOfDay);
    }

    // Thống kê số đơn hàng thất bại trong ngày
    public long countFailedOrdersByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return transportRepository.countFailedOrdersByDate(startOfDay, endOfDay);
    }
    // thống kê tổng số đơn hàng thành công và thất bại
    
    public long getTotalSuccessfulOrders() {
        return transportRepository.countAllSuccessfulOrders();
    }

    public long getTotalFailedOrders() {
        return transportRepository.countAllFailedOrders();
    }
    
    // tính doanh thu từ các đơn hàng thành công ( ngày và tổng)
    
	/*
	 * public double calculateTotalRevenueFromSuccessfulOrders() {
	 * 
	 * 
	 * List<transportProduct> successfulOrders =
	 * transportRepository.findByOrderStatus(true); return successfulOrders.stream()
	 * .mapToDouble(order ->
	 * oder_productService.calculateTotalPrice(order.getOrderProduct())) .sum(); }
	 */


}
