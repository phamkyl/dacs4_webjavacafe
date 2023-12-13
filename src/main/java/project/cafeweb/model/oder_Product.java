package project.cafeweb.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="oder_product")
public class oder_Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_type_id", nullable = false)
    private categoryProduct category;

    @Min(value = 1, message = "Số lượng không hợp lệ")
    @NotNull(message = "Số lượng không được để trống")
    @Column(name = "quantity_oder")
    private Integer quantityOder;

    @Column(name = "aprice")
    private Double aprice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "address")
    private String address;

    private String nameCustomer;
    private String email;
    private String sdt;
    private String note;
    
    private boolean orderConfirm;
    private boolean CancleOrder;
    
    
    public boolean isCancleOrder() {
		return CancleOrder;
	}
	public void setCancleOrder(boolean cancleOrder) {
		CancleOrder = cancleOrder;
	}

	@OneToOne(mappedBy = "orderProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private transportProduct transportProduct;
    
  
    public transportProduct getTransportProduct() {
		return transportProduct;
	}
	public void setTransportProduct(transportProduct transportProduct) {
		this.transportProduct = transportProduct;
	}
	public boolean isOrderConfirm() {
        return orderConfirm;
    }
	public void setOrderConfirm(boolean orderConfirm) {
		this.orderConfirm = orderConfirm;
	}

	@Column(name = "date_order")
    private LocalDateTime dateOrder;



    @PrePersist
    protected void onCreate() {
        dateOrder = LocalDateTime.now();
    }

    @PostUpdate
    protected void onUpdate() {
        dateOrder = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public categoryProduct getCategory() {
		return category;
	}

	public Integer getQuantityOder() {
		return quantityOder;
	}

	public Double getAprice() {
		return aprice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public String getAddress() {
		return address;
	}

	public String getNameCustomer() {
		return nameCustomer;
	}

	public String getEmail() {
		return email;
	}

	public String getSdt() {
		return sdt;
	}

	public String getNote() {
		return note;
	}

	public LocalDateTime getDateOrder() {
		return dateOrder;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setCategory(categoryProduct category) {
		this.category = category;
	}

	public void setQuantityOder(Integer quantityOder) {
		this.quantityOder = quantityOder;
	}

	public void setAprice(Double aprice) {
		this.aprice = aprice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setDateOrder(LocalDateTime dateOrder) {
		this.dateOrder = dateOrder;
	}

	public oder_Product(Product product, categoryProduct category,
			@Min(value = 1, message = "Số lượng không hợp lệ") @NotNull(message = "Số lượng không được để trống") Integer quantityOder,
			Double aprice, Double totalPrice, String address, String nameCustomer, String email, String sdt,
			String note, LocalDateTime dateOrder, boolean orderConfirm, transportProduct transportProduct, boolean CancleOrder ) {
		super();
		this.product = product;
		this.category = category;
		this.quantityOder = quantityOder;
		this.aprice = aprice;
		this.totalPrice = totalPrice;
		this.address = address;
		this.nameCustomer = nameCustomer;
		this.email = email;
		this.sdt = sdt;
		this.note = note;
		this.dateOrder = dateOrder;
		this.orderConfirm = orderConfirm;
		this.transportProduct = transportProduct;
		this.CancleOrder =CancleOrder;
	}
		

	  public oder_Product() {
		super();
	}
	public oder_Product(Long orderId) {
	        this.id = orderId;
	    }

	}
    
    
