package com.substring.helpdesk.repository;

import com.substring.helpdesk.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long> {
   // Optional<Ticket> findByTicketId(Long ticketId);
    Optional<Ticket> findByEmail(String email);

}
