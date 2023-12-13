package project.cafeweb.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.cafeweb.model.oder_Product;

@Repository
public interface oder_productRepository  extends JpaRepository<oder_Product, Long>{


    @Query("SELECT COUNT(o) FROM oder_Product o WHERE o.orderConfirm = true AND o.dateOrder >= :startDate AND o.dateOrder < :endDate")
    long countConfirmedOrdersByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM oder_Product o WHERE o.CancleOrder = true AND o.dateOrder >= :startDate AND o.dateOrder < :endDate")
    long countCanceledOrdersByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM oder_Product o WHERE o.dateOrder >= :startDate AND o.dateOrder < :endDate")
    long countTotalOrdersByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
