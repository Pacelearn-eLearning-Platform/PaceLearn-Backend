package com.charusat.pacelearn.Service;

import com.charusat.pacelearn.Entity.Authority;
import com.charusat.pacelearn.Entity.User;
import com.charusat.pacelearn.Repository.AuthorityRepository;
import com.charusat.pacelearn.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public void initAuthorityAndUser() {
//
//        Authority adminAuthority = new Authority();
//        adminAuthority.setAuthorityName("Admin");
//        adminAuthority.setAuthorityDescription("Admin role");
//        roleRepository.save(adminAuthority);
//
//        Authority userAuthority = new Authority();
//        userAuthority.setAuthorityName("User");
//        userAuthority.setAuthorityDescription("Default role for newly created record");
//        roleRepository.save(userAuthority);
//
//        User adminUser = new User();
//        adminUser.setUserName("admin123");
//        adminUser.setUserEmail("admin123@gmail.com");
//        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
//        adminUser.setUserFirstName("admin");
//        adminUser.setUserLastName("admin");
//        Set<Authority> adminAuthoritys = new HashSet<>();
//        adminAuthoritys.add(adminAuthority);
//        adminUser.setAuthorities(adminAuthoritys);
//        userRepository.save(adminUser);
//
////        User user = new User();
////        user.setUserName("raj123");
////        user.setUserPassword(getEncodedPassword("raj@123"));
////        user.setUserFirstName("raj");
////        user.setUserLastName("sharma");
////        Set<Authority> userAuthoritys = new HashSet<>();
////        userAuthoritys.add(userAuthority);
////        user.setAuthority(userAuthoritys);
////        userRepository.save(user);
//    }

    public User registerNewUser(User user) {

            Authority role = authorityRepository.findById("User").get();
            Set<Authority> userAuthoritys = new HashSet<>();
            userAuthoritys.add(role);
            user.setAuthorities(userAuthoritys);
            user.setUserPassword(getEncodedPassword(user.getUserPassword()));
            return userRepository.save(user);

    }


    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
