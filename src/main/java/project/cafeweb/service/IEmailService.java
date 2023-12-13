package project.cafeweb.service;

import project.cafeweb.model.oder_Product;

public interface IEmailService {
	 void sendConfirmationEmail(oder_Product order) throws Exception;
}
