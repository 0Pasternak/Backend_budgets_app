package com.profilerenovation.security.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.profilerenovation.entity.User;

@Service
public class ProfileUserDetails implements UserDetails {

	private Long id;
	private String email;
	private String password;
	private Collection<GrantedAuthority> authorities;

	public ProfileUserDetails() {
	}

	public ProfileUserDetails(Long id, String email, String password, Collection<GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static ProfileUserDetails buildUserDetails(User user) {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
		return new ProfileUserDetails(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				Collections.singletonList(authority));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
