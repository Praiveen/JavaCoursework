package com.example.PcShop.services;

import com.example.PcShop.entities.Order;
import com.example.PcShop.entities.Role;
import com.example.PcShop.entities.User;
import com.example.PcShop.repositories.RoleRepository;
import com.example.PcShop.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean saveUser(User user, String key) {
        System.out.println("Here");
        if (key == "update"){
            userRepository.save(user);
            return true;
        }
        User userFromDB = userRepository.findByEmail(user.getEmail());
        System.out.println(userFromDB);

        if (userFromDB != null) {
            System.out.println("dubl");
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        System.out.println("eee");
        userRepository.save(user);
        return true;
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

//    public List<User> findByRole(String roleName) {
//        // Находим роль по ее имени
//        Role role = roleRepository.findByName(roleName);
//        if (role != null) {
//            System.out.println("here");
//            // Получаем всех пользователей с данной ролью
//            return role.getUsers().stream().collect(Collectors.toList());
//        } else {
//            // Если роль не найдена, возвращаем пустой список
//            return List.of();
//        }
//    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updateUserBanStatus(Long userId) {
        // Найдем пользователя по его идентификатору
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getAccountBan()){
                user.setAccountBan(false);
            }
            else {
                user.setAccountBan(true);
            }
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

//    @Override
//    public List<UserDto> findAllUsers() {
//        List<User> users = userRepository.findAll();
//        return users.stream().map((user) -> convertEntityToDto(user))
//                .collect(Collectors.toList());
//    }

//    private UserDto convertEntityToDto(User user){
//        UserDto userDto = new UserDto();
//        String[] name = user.getName().split(" ");
//        userDto.setFirstName(name[0]);
//        userDto.setLastName(name[1]);
//        userDto.setEmail(user.getEmail());
//        return userDto;
//    }

//    private Role checkRoleExist() {
//        Role role = new Role();
//        role.setName("ROLE_ADMIN");
//        return roleRepository.save(role);
//    }

}