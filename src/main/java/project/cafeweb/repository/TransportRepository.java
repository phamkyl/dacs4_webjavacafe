package project.cafeweb.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.cafeweb.model.transportProduct;

@Repository
public interface TransportRepository extends JpaRepository<transportProduct, Long> {
	 @Query("SELECT COUNT(t) FROM transportProduct t WHERE t.orderStatus = true AND t.dateTransport BETWEEN :startDate AND :endDate")
	    long countSuccessfulOrdersByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	    @Query("SELECT COUNT(t) FROM transportProduct t WHERE t.orderStatus = false AND t.dateTransport BETWEEN :startDate AND :endDate")
	    long countFailedOrdersByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	    
	    // Optionally, you can also add a method to get all orders by date
	    List<transportProduct> findAllByDateTransportBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	List<transportProduct> findByOrderStatus(boolean b);
	@Query("SELECT COUNT(t) FROM transportProduct t WHERE t.orderStatus = true")
	long countAllSuccessfulOrders();
	 @Query("SELECT COUNT(t) FROM transportProduct t WHERE t.orderStatus = false")
	long countAllFailedOrders();





	
	
	

}
