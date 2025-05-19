package br.app.mentesaudavel.api.modules.authentication.infrastructure.services;

import br.app.mentesaudavel.api.modules.authentication.domain.model.UserDetailsImpl;
import br.app.mentesaudavel.api.modules.user.repositories.UserRepository;
import br.app.mentesaudavel.api.shared.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public UserDetails loadUserBydId(UUID id) {
        return this.userRepository.findById(id)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new ResourceNotFoundException("User not found.", null));
    }
}
