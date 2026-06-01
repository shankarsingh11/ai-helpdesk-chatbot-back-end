package com.substring.helpdesk.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Query_ConversationID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String query;

    @Column
    private String conversation_id;

}
