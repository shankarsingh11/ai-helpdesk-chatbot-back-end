package com.substring.helpdesk.entity;

import com.substring.helpdesk.entity.enm.AuthProvider;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;
	

}
