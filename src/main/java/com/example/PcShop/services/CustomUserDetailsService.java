package com.example.PcShop.services;

import com.example.PcShop.entities.Role;
import com.example.PcShop.entities.User;
import com.example.PcShop.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private User user;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//
//        if (user != null) {
//            return new org.springframework.security.core.userdetails.User(user.getEmail(),
//                    user.getPassword(),
//                    mapRolesToAuthorities(user.getRoles()));
//        }else{
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new CustomUserDetails(user);
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
        private final Long userId;
        private final User user;

        public CustomUserDetails(User user) {
            super(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
            this.userId = user.getId();
            this.user = user;
        }

        public User getUser(){
            return user;
        }

        public Long getUserId() {
            return userId;
        }

        private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }
    }



//    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
//        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return mapRoles;
//    }
}