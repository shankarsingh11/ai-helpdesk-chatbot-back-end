package com.substring.helpdesk.entity;

import com.substring.helpdesk.entity.enm.AuthProvider;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(nullable = false,length = 100)
    private String name;

    // Null for Google/GitHub users
	@Column
	private String password;

    @Column(nullable = false, unique = true,length = 150)
    private String email;

    private String profilePicture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;

    private String providerId;

    @Column(nullable = false)
    private boolean enabled = true;

}
