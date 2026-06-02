package com.substring.helpdesk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="user")
@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(nullable = false)
    private String name;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false,unique = true)
	private String password;
    @Column(nullable = false, unique = true)
    private String email;
	

}
