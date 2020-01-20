package com.clane.test.security;

import com.clane.test.model.Author;
import com.clane.test.service.AuthorService;
import com.clane.test.util.MessageUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final AuthorService authorService;

	public JwtUserDetailsService(AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Author author = authorService.getAuthorByUsername(username);
		if (!StringUtils.isEmpty(author)) {

			//Assign role AUTHOR to everyone who successfully logged in
			return new User(author.getEmail(), author.getPassword(),
					new ArrayList<String>(){{add("ROLE_AUTHOR");}});
		} else {
			throw new UsernameNotFoundException(
					MessageUtil.msg("author_username_not_found").replace("{}", username));
		}
	}
}