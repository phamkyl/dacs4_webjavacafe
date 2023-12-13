package project.cafeweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name= "category")
public class categoryProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_type_id")
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id=id;
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

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public boolean getIsDeleted(){
        return this.isDeleted;
    }
    public void setIsDeleted(boolean isDeleted){
        this.isDeleted=isDeleted;
    }
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public categoryProduct(@NotEmpty(message = "Name can't be empty!") String name, boolean isDeleted) {
		super();
		this.name = name;
		this.isDeleted = isDeleted;
	}
	public categoryProduct() {
		super();
	}
    
	
	

}
