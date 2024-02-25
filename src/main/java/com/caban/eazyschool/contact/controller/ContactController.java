package com.caban.eazyschool.contact.controller;

import com.caban.eazyschool.contact.model.Contact;
import com.caban.eazyschool.contact.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String displayContactPage() {
        return "contact";
    }

    @PostMapping("/saveMsg")
    public String saveMessage(Contact contact) {
        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }
}
