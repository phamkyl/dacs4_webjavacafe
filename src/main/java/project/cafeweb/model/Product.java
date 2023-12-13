package project.cafeweb.model;


import java.util.Base64;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.Transient;
@Entity
@Transient 
@Table(name = "product")
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;


    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    @Column(name = "product_type_id")
    @NotNull(message = "Select Product type!")
    private Integer productTypeId;

    public Integer getProductTypeId(){
        return this.productTypeId;
    }
    public void setProductTypeId(Integer productTypeId){
        this.productTypeId=productTypeId;
    }

    @NotEmpty(message = "Name can't be empty!")
    @Column(name = "name")
    private String name;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    @Column(name = "brand")
    private String brand;

    public String getBrand(){
        return this.brand;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }

    @Column(name = "madein")
    private String madein;
    

    public String getMadein(){
        return this.madein;
    }
    public void setMadein(String madein){
        this.madein=madein;
    }

    @Column(name = "price")
    private float price;

    public float getPrice(){
        return this.price;
    }
    public void setPrice(float price){
        this.price=price;
    }

    @Column(name = "is_deleted")
    private boolean isDeleted;


    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


	@Column(name = "detail", length = 10000)

    private String detail;
    
    public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name ="title")
    private String title;
    
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name ="quantity")
    private int quantity;
    @Column(name = "before_quantity")
    private int before_quantity;
    
    
    public int getBefore_quantity() {
		return before_quantity;
	}
	public void setBefore_quantity(int before_quantity) {
		this.before_quantity = before_quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(boolean isDeleted){
        this.isDeleted=isDeleted;
    }
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
		

	public Product(@NotNull(message = "Select Product type!") Integer productTypeId,
			@NotEmpty(message = "Name can't be empty!") String name, String brand, String madein, float price,
			boolean isDeleted, byte[] image, String detail, String title, int quantity, int before_quantity) {
		super();
		this.productTypeId = productTypeId;
		this.name = name;
		this.brand = brand;
		this.madein = madein;
		this.price = price;
		this.isDeleted = isDeleted;
		this.image = image;
		this.detail = detail;
		this.title = title;
		this.quantity = quantity;
		this.before_quantity = before_quantity;
	}
	public Product() {
		super();
	}
	
	  public String getImageBase64() {
	        return Base64.getEncoder().encodeToString(image);
	    }

	
	
	
}
	
	
	
	
	