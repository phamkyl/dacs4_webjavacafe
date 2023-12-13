package project.cafeweb.service;

import java.io.ByteArrayOutputStream;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import project.cafeweb.model.oder_Product;

@Service
public class EmailServiceImpl {
	private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendConfirmationEmail(oder_Product order) throws Exception {
    	try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(order.getEmail());
            helper.setSubject("Order Confirmation");
            helper.setText(createConfirmationEmailContent(order), true);

            // Attach PDF
            ByteArrayResource pdfAttachment = createPdfAttachment(order);
            helper.addAttachment("Shipping_Label.pdf", pdfAttachment);

            javaMailSender.send(message);

            // Log success
            System.out.println("Confirmation email sent successfully to: " + order.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            // Log error
            System.err.println("Failed to send confirmation email. Error: " + e.getMessage());
        }
    }

	private ByteArrayResource createPdfAttachment(oder_Product order) throws DocumentException {
		// Tạo HTML từ template Thymeleaf
        String htmlContent = createConfirmationEmailContent(order);

        // Tạo file PDF từ HTML
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);

        // Chuyển đổi ByteArrayOutputStream thành ByteArrayResource
        byte[] bytes = outputStream.toByteArray();
        return new ByteArrayResource(bytes);
	}

	private String createConfirmationEmailContent(oder_Product order) {
		StringBuilder content = new StringBuilder();

        content.append("<html>")
               .append("<body>")
               .append("<h2>Order Confirmation</h2>")
               .append("<p>Thank you for your order. Here are the details:</p>")
               .append("<p>Order ID: ").append(order.getId()).append("</p>")
               .append("<p>Product: ").append(order.getProduct().getName()).append("</p>")
               .append("<p>Category: ").append(order.getCategory().getName()).append("</p>")
               .append("<p>Name: ").append(order.getNameCustomer()).append("</p>")
               .append("<p>Email: ").append(order.getEmail()).append("</p>")
               .append("<p>Phone: ").append(order.getSdt()).append("</p>")
               .append("<p>Address: ").append(order.getAddress()).append("</p>")
               .append("<p>Quantity: ").append(order.getQuantityOder()).append("</p>")
               .append("<p>Total Price: ").append(order.getTotalPrice()).append("</p>")
               .append("<p>Date Order: ").append(order.getDateOrder()).append("</p>")
               .append("</body>")
               .append("</html>");

        return content.toString();
	}
// đơn hàng hủy
	public void sendCancellationEmail(oder_Product order) {
		try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(order.getEmail());
            helper.setSubject("Order Confirmation");
            helper.setText(createConfirmationEmailContentCancle(order), true);

            // Attach PDF
            ByteArrayResource pdfAttachment = createPdfAttachment(order);
            helper.addAttachment("Shipping_Label.pdf", pdfAttachment);

            javaMailSender.send(message);

            // Log success
            System.out.println("Confirmation email sent successfully to: " + order.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            // Log error
            System.err.println("Failed to send confirmation email. Error: " + e.getMessage());
        }
		
	}
	
	private String createConfirmationEmailContentCancle(oder_Product order) {
		StringBuilder content = new StringBuilder();

        content.append("<html>")
               .append("<body>")
               .append("<h2>Order Confirmation Cancle</h2>")
               .append("<p>Unfortunately, your order has been canceled because you did not confirm the order or choose to cancel the order through the website or call us to confirm. "
               		+ "We are really sorry . "
               		+ "Hope you will order again/see you in the near future. "
               		+ "have a good day Here are the details:</p>")
               .append("<p>Order ID: ").append(order.getId()).append("</p>")
               .append("<p>Product: ").append(order.getProduct().getName()).append("</p>")
               .append("<p>Category: ").append(order.getCategory().getName()).append("</p>")
               .append("<p>Name: ").append(order.getNameCustomer()).append("</p>")
               .append("<p>Email: ").append(order.getEmail()).append("</p>")
               .append("<p>Phone: ").append(order.getSdt()).append("</p>")
               .append("<p>Address: ").append(order.getAddress()).append("</p>")
               .append("<p>Quantity: ").append(order.getQuantityOder()).append("</p>")
               .append("<p>Total Price: ").append(order.getTotalPrice()).append("</p>")
               .append("<p>Date Order: ").append(order.getDateOrder()).append("</p>")
               .append("</body>")
               .append("</html>");

        return content.toString();
	}
    
    
}
