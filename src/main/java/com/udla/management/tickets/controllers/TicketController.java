package com.udla.management.tickets.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udla.management.tickets.models.TicketModel;
import com.udla.management.tickets.services.TicketService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping(path="/tickets")
public class TicketController {

    private final TicketService ticketService;

    
    @GetMapping("/getTickets")
    public List<TicketModel> getTicktes() {
        return ticketService.getTicktes();
    }
    
    @GetMapping("/getTicketById/{id}")
    public TicketModel getTicketById (@PathVariable Long id) {
        return ticketService.findTicket(id);
    }

    @PostMapping("/saveTicket")
    public String saveTicket(@RequestBody TicketModel ticket) {
        ticketService.saveTicket(ticket);
        return "Ticket saved";
    }

    @PutMapping("/editTicket")
    public String editTicket(@RequestBody TicketModel ticket) {
        ticketService.editTicket(ticket);
        return "Ticket edited";
    }

    @DeleteMapping("/deleteTicket")
    public String deleteTicket(Long id) {
        ticketService.deleteTicket(id);
        return "Ticket with id " +id +" was deleted";
    }
}