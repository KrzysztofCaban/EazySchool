package com.caban.eazyschool.rest;

import com.caban.eazyschool.constants.EazySchoolConstants;
import com.caban.eazyschool.model.Contact;
import com.caban.eazyschool.model.Response;
import com.caban.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class ContactRestController {

    private final ContactService contactService;

    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatus(@RequestParam(name = "status") String status) {

        return contactService.findContactsByStatus(status);
    }

    @GetMapping("/getAllMsgsByStatus")
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact) {
        if (contact != null && contact.getStatus() != null)
            return contactService.findContactsByStatus(contact.getStatus());

        return Collections.emptyList();
    }

    @PostMapping
    public ResponseEntity<Response> saveMsg(@Valid @RequestBody Contact contact) {

        contactService.saveMessageDetails(contact);

        Response response = new Response();
        response.setStatusCode(HttpStatus.CREATED.toString());
        response.setStatusMsg(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteMsg(Contact contact) {
        Response response = new Response();

        Optional<Contact> optContact = contactService.findContactById(contact.getContactId());

        if (optContact.isPresent()) {
            contactService.deleteMessage(optContact.get());

            response.setStatusCode(HttpStatus.OK.toString());
            response.setStatusMsg(HttpStatus.OK.getReasonPhrase());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }

        response.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        response.setStatusMsg(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @PatchMapping
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq) {
        Optional<Contact> optContact = contactService.findContactById(contactReq.getContactId());

        Response response = new Response();

        if (optContact.isPresent()) {
            optContact.get().setStatus(EazySchoolConstants.CLOSE);
            System.out.println(optContact.get());
            contactService.saveMessageDetails(optContact.get());

            response.setStatusCode(HttpStatus.OK.toString());
            response.setStatusMsg(HttpStatus.OK.getReasonPhrase());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }

        response.setStatusCode(HttpStatus.BAD_REQUEST.toString());
        response.setStatusMsg(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
