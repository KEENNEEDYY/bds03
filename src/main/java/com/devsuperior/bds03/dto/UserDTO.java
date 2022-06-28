package com.devsuperior.bds03.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;

import com.devsuperior.bds03.entities.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	@Email(message = "Favor entrar com email válido")
	private String email;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(){
    }   

    public UserDTO(Long id, String email) {
		this.id = id;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

}