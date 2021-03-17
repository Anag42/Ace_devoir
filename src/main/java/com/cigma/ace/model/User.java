package com.cigma.ace.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.cigma.ace.annotation.Unique;
import com.cigma.ace.enums.Role;
import com.cigma.ace.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@NotBlank(message = "*Please provide an email")
	@Email(message = "*Please provide a valid email")

	@Column(unique = true)
	private String email;

	@NotBlank(message = "*Please provide an user name")
	@Column(unique = true)
	private String username;

	private String password;

	@Pattern(regexp = "(^$|0[7|6][0-9]{8})")
	private String telephone;

	@Enumerated(EnumType.STRING)
	private Role role = Role.CLIENT;

	@CreationTimestamp
	private Date createdAt;

	@UpdateTimestamp
	private Date updatedAt;
}
