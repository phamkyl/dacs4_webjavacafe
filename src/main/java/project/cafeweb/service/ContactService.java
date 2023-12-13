package project.cafeweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.cafeweb.model.Product;
import project.cafeweb.model.contact;
import project.cafeweb.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public void saveContact(contact contactForm) {
       contact contact = convertToEntity(contactForm);
        contactRepository.save(contact);
    }

    private contact convertToEntity(contact contactForm) {
        contact contact = new contact();
        contact.setSubject(contactForm.getSubject());
        contact.setName(contactForm.getName());
        contact.setPhone(contactForm.getPhone());
        contact.setEmail(contactForm.getEmail());
        contact.setContext(contactForm.getContext());
        return contact;
    }

	public List<contact> listAll() {
		// TODO Auto-generated method stub
		return contactRepository.findAll();
	}
}
