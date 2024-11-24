package com.procrastination.security;

import com.procrastination.domain.entity.UserEntity;
import com.procrastination.exception.user.UserNotFoundException;
import com.procrastination.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return new CustomUserDetails(userEntity);
    }
}
