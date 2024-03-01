package com.caban.eazyschool.security;

import com.caban.eazyschool.model.Person;
import com.caban.eazyschool.model.Roles;
import com.caban.eazyschool.repository.PersonRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EazySchoolUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public EazySchoolUsernamePwdAuthenticationProvider(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = personRepository.findByEmail(email);

        if (person != null && passwordEncoder.matches(pwd, person.getPwd()))
            return new UsernamePasswordAuthenticationToken(
                    person.getName(), null, getGrantedAuthorities(person.getRoles())
            );
        else
            throw new BadCredentialsException("Invalid credentials!");
    }

    private List<GrantedAuthority> getGrantedAuthorities(Roles roles) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + roles.getRoleName()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
