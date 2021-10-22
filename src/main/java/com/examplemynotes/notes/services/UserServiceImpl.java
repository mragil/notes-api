package com.examplemynotes.notes.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import com.examplemynotes.notes.models.Role;
import com.examplemynotes.notes.models.User;
import com.examplemynotes.notes.repositories.RoleRepo;
import com.examplemynotes.notes.repositories.UserRepo;
import com.examplemynotes.notes.security.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor // DI with Lombok
@Transactional
@Slf4j // Logging with Lombok
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in db");
            throw new UsernameNotFoundException("Username not exist!");
        } else {
            log.info("User found {}", user.getName());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new UserDetailsImpl(user.getId(), user.getName(), user.getUsername(),
                user.getPassword(), authorities);
        // return new org.springframework.security.core.userdetails.User(user.getUsername(),
        // user.getPassword(),
        // authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user = {}", user.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role = {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);

        log.info("Adding new role to the user");

    }

    @Override
    public User getUser(String username) {
        log.info("Get User by username = {}", username);

        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Get all users ");
        return userRepo.findAll();
    }


}
