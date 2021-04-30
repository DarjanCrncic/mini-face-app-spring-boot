package com.example.minifaceapp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.minifaceapp.model.FaceUser;

public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 4132882500242318971L;
	
	private FaceUser faceUser;
	
	public CustomUserDetails(FaceUser faceUser) {
		this.faceUser = faceUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return faceUser.getPassword();
	}

	@Override
	public String getUsername() {
		return faceUser.getUsername();
	}
	
	public Long getId() {
		return faceUser.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
