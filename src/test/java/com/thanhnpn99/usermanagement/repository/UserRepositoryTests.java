package com.thanhnpn99.usermanagement.repository;

import com.thanhnpn99.usermanagement.entity.Role;
import com.thanhnpn99.usermanagement.entity.User;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Junit test for save user operation
    @DisplayName("Junit test for save user operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user = User.builder()
                .name("Nguyen Phuc Ngoc Thanh")
                .username("ngocthanhnpn1999")
                .email("ngocthanhpnqb@gmail.com")
                .password("123")
                .roles(roles)
                .build();

        // when - action or the behavior that we are going test
        User savedUser = userRepository.save(user);

        // then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    // JUnit test for get all employees operation
    @DisplayName("JUnit test for get all user operation")
    @Test
    public void givenUserList_whenFindAll_thenUserList(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user1 = User.builder()
                .name("Dang Quang Lam")
                .username("quanglamdang2k")
                .email("quanglamdang2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();

        User user2 = User.builder()
                .name("Tu Nhat Khanh")
                .username("khanhnhatu2k")
                .email("khanhnhatu2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // when -  action or the behaviour that we are going test
        List<User> userList = userRepository.findAll();

        // then - verify the output
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(5);

    }

    @DisplayName("JUnit test for get user by username or email operation")
    @Test
    public void givenUserObject_whenFindByUsernameOrEmail_thenReturnUserObject(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user1 = User.builder()
                .name("Dang Quang Lam")
                .username("quanglamdang2k")
                .email("quanglamdang2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();
        userRepository.save(user1);

        // when -  action or the behaviour that we are going test
//        Optional<User> userOptional = userRepository.findByUsernameOrEmail(user1.getUsername(),user1.getEmail());
//        Optional<User> userOptional = userRepository.findByUsernameOrEmail("",user1.getEmail());
        Optional<User> userOptional = userRepository.findByUsernameOrEmail(user1.getUsername(),"");

        // then - verify the output
        assertThat(userOptional).isNotNull();
    }

    @DisplayName("JUnit test for get user by username or email operation")
    @Test
    public void givenUserObject_whenExistsByUsername_thenReturnBoolean(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user1 = User.builder()
                .name("Dang Quang Lam")
                .username("quanglamdang2k")
                .email("quanglamdang2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();
        userRepository.save(user1);

        // when -  action or the behaviour that we are going test
        boolean existsUser = userRepository.existsByUsername(user1.getUsername());

        // then - verify the output
        assertThat(existsUser).isTrue();
    }

    @DisplayName("JUnit test for get user by username or email operation")
    @Test
    public void givenUserObject_whenExistsByEmail_thenReturnBoolean(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user1 = User.builder()
                .name("Dang Quang Lam")
                .username("quanglamdang2k")
                .email("quanglamdang2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();
        userRepository.save(user1);

        // when -  action or the behaviour that we are going test
        boolean existsUser = userRepository.existsByEmail("emailcuanguoiBinhDuong@gmail.com");

        // then - verify the output
        assertThat(existsUser).isFalse();
    }
    @DisplayName("JUnit test for get user by username or email operation")
    @Test
    public void givenRoleObject_whenFindByName_thenReturnRoleObject(){

        // given - precondition or setup
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        User user1 = User.builder()
                .name("Dang Quang Lam")
                .username("quanglamdang2k")
                .email("quanglamdang2k@gmail,com")
                .password("123")
                .roles(roles)
                .build();
        userRepository.save(user1);

        // when -  action or the behaviour that we are going test
        Role roleFindByName = roleRepository.findByName(userRole.getName()).get();

        // then - verify the output
        assertThat(roleFindByName).isNotNull();
    }
}