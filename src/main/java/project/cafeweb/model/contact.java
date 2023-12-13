package project.cafeweb.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

@Entity
public class contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// chủ đề - vấn đề gì
	@Column(name = "subject")
	private String subject;
	// tên liên hệ của khách hàng
	@Column(name ="nameCustomer")
	private String name;
	// stđ liên hệ
	@Column(name ="phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	// nội dung kiếu nại
	@Column(name ="context")
	private String context;
	
	// ngày gửi
	@Column(name = "datesend")
	private LocalDateTime dateSend;
	

    @PrePersist
    protected void onCreate() {
    	dateSend = LocalDateTime.now();
    }

    @PostUpdate
    protected void onUpdate() {
    	dateSend = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getContext() {
		return context;
	}

	public LocalDateTime getDateSend() {
		return dateSend;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public void setDateSend(LocalDateTime dateSend) {
		this.dateSend = dateSend;
	}

	public contact(String subject, String name, String phone, String email, String context, LocalDateTime dateSend) {
		super();
		this.subject = subject;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.context = context;
		this.dateSend = dateSend;
	}

	public contact() {
		super();
	}

	
	
	

}
