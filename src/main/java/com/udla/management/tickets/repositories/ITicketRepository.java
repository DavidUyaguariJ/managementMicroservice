package com.udla.management.tickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udla.management.tickets.models.TicketModel;

@Repository
public interface ITicketRepository extends JpaRepository<TicketModel, Long> {
    
}
