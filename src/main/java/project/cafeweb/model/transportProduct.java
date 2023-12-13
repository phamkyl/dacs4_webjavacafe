package project.cafeweb.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "transport_product")
public class transportProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_transport")
    private LocalDateTime dateTransport;

    @Column(name = "successProductDate")
    private LocalDateTime successProductDate;

    @Column(name = "date_create")
    private LocalDateTime creationDate;

    @Column(name = "reason")
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private oder_Product orderProduct;

    @Column(name = "status")
    private boolean orderStatus;

    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }

    @PostUpdate
    protected void onUpdate() {
        creationDate = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getReason() {
		return reason;
	}

	public oder_Product getOrderProduct() {
		return orderProduct;
	}

	public boolean isOrderStatus() {
		return orderStatus;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setOrderProduct(oder_Product orderProduct) {
		this.orderProduct = orderProduct;
	}

	public void setOrderStatus(boolean orderStatus) {
		this.orderStatus = orderStatus;
	}

	

	public LocalDateTime getDateTransport() {
		return dateTransport;
	}

	public LocalDateTime getSuccessProductDate() {
		return successProductDate;
	}

	public void setDateTransport(LocalDateTime dateTransport) {
		this.dateTransport = dateTransport;
	}

	public void setSuccessProductDate(LocalDateTime successProductDate) {
		this.successProductDate = successProductDate;
	}
	
	

	public transportProduct(LocalDateTime dateTransport, LocalDateTime successProductDate, LocalDateTime creationDate,
			String reason, oder_Product orderProduct, boolean orderStatus) {
		super();
		this.dateTransport = dateTransport;
		this.successProductDate = successProductDate;
		this.creationDate = creationDate;
		this.reason = reason;
		this.orderProduct = orderProduct;
		this.orderStatus = orderStatus;
	}

	public transportProduct() {
		super();
	}

	

   

   
    
}