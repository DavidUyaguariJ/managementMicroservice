package com.udla.management.tickets.services;

import java.util.List;

import com.udla.management.tickets.models.TicketModel;

public interface ITicketService {

    public List<TicketModel> getTicktes();

    public void saveTicket(TicketModel ticket);

    public void deleteTicket(Long id);

    public TicketModel findTicket(Long id);

    public void editTicket (TicketModel ticket);
}