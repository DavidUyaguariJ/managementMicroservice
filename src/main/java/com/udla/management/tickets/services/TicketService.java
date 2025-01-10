package com.udla.management.tickets.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udla.management.tickets.models.TicketModel;
import com.udla.management.tickets.repositories.ITicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final ITicketRepository ticketRepository;

    @Override
    public List<TicketModel> getTicktes() {
        return ticketRepository.findAll();
    }

    @Override
    public void saveTicket(TicketModel ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public TicketModel findTicket(Long id) {
        if(findTicketValue(id)){
            throw new RuntimeException("Ticket not found");
        }
        return ticketRepository.findById(id).orElse(null);
    }

    @Override
    public void editTicket(TicketModel ticket) {
        if(findTicketValue(ticket.getId())){
            throw new RuntimeException("Ticket not found");
        }
        ticketRepository.save(ticket);
    }

    private boolean findTicketValue(Long id) {
        return ticketRepository.findById(id).orElse(null) == null;
    }
}