package com.thanhnpn99.usermanagement.service;

import com.thanhnpn99.usermanagement.dto.LoginDto;
import com.thanhnpn99.usermanagement.dto.SignUpDto;
import com.thanhnpn99.usermanagement.entity.Role;
import com.thanhnpn99.usermanagement.entity.User;
import com.thanhnpn99.usermanagement.repository.RoleRepository;
import com.thanhnpn99.usermanagement.repository.UserRepository;
import com.thanhnpn99.usermanagement.security.JwtTokenProvider;
import com.thanhnpn99.usermanagement.service.impl.AuthServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceImplTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    @BeforeEach
    void setup(){
        Role userRole = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user = User.builder()
                .id(10L)
                .name("Nguyen Phuc Ngoc Thanh")
                .username("ngocthanhnpn1999")
                .email("ngocthanhxs2@gmail.com")
                .password("123")
                .active(true)
                .roles(roles)
                .build();
    }

    @DisplayName("Junit test for getAllUser method")
    @Test
    public void givenUser_whenLogin_thenString() throws Exception {
        // given - precondition or setup
        LoginDto loginDto = LoginDto.builder()
                .usernameOrEmail("ngocthanhxs2@gmail.com")
                .password("123")
                .build();

        given(userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),loginDto.getUsernameOrEmail()))
                .willReturn(Optional.of(user));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        // when - action or behavior that we are going test
        String login = authService.login(loginDto);

        // then - verify the output
        Assertions.assertThat(login).isNotNull();
        Assertions.assertThat(login).isEqualTo(token);
    }


    //Junit test for register method
    @DisplayName("Junit test for register method")
    @Test
    public void givenUserObject_whenRegister_thenReturnString() throws Exception {
        // given - precondition or setup
        SignUpDto registerDto = SignUpDto.builder()
                .name("Nguyen Van Tam")
                .username("tamvannguyen1999")
                .email("tamvannguyen99@gmail.com")
                .password("123")
                .build();
        given(userRepository.existsByUsername(registerDto.getUsername()))
                .willReturn(false);

        given(userRepository.existsByEmail(registerDto.getEmail()))
                .willReturn(false);

        User user = new User();
        user.setId(10L);
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        this.passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = Role.builder().id(2L).name("ROLE_USER").build();
        Set<Role> roles = new HashSet<>();
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        roles.add(userRole);
        user.setRoles(roles);
        given(userRepository.save(user)).willReturn(user);

        // when - action or behavior that we are going test
        String register = authService.register(registerDto);

        // then - verify the output
        Assertions.assertThat(register).isNotNull();
        Assertions.assertThat(register).isEqualTo("User registered successfully!.");
    }

    @DisplayName("Junit test for getAllUser method")
    @Test
    public void givenUser_whenGetAllUsers_thenListUser() throws Exception {
        // given - precondition or setup
        User user1 = User.builder()
                .id(11L)
                .name("Nguyen Van Tam")
                .username("tamvannguyen1999")
                .email("tamvannguyen99@gmail.com")
                .password("123")
                .build();
        given(userRepository.findAll()).willReturn(List.of(user,user1));
        // when - action or behavior that we are going test
        List<User> userList = authService.getAllUsers();

        // then - verify the output
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

}
