package com.enigma.group5.save_it_backend.service.Impl;

import com.enigma.group5.save_it_backend.constant.UserRole;
import com.enigma.group5.save_it_backend.dto.request.AuthRequest;
import com.enigma.group5.save_it_backend.dto.request.RegisterRequest;
import com.enigma.group5.save_it_backend.dto.response.LoginResponse;
import com.enigma.group5.save_it_backend.dto.response.RegisterResponse;
import com.enigma.group5.save_it_backend.entity.Customer;
import com.enigma.group5.save_it_backend.entity.Role;
import com.enigma.group5.save_it_backend.entity.UserAccount;
import com.enigma.group5.save_it_backend.repository.UserAccountRepository;
import com.enigma.group5.save_it_backend.service.AuthService;
import com.enigma.group5.save_it_backend.service.CustomerService;
import com.enigma.group5.save_it_backend.service.JwtService;
import com.enigma.group5.save_it_backend.service.RoleService;
import com.enigma.group5.save_it_backend.utils.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final JwtService jwtService;
    private final ValidationUtil validationUtil;

    private final AuthenticationManager authenticationManager;

    @Value("${save_it.superadmin.username}")
    private String superAdminUsername;

    @Value("${save_it.superadmin.email}")
    private String superAdminEmail;

    @Value("${save_it.superadmin.password}")
    private String superAdminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername(superAdminUsername);
        if (currentUser.isPresent()) {
            return;
        }

        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        UserAccount account = UserAccount.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .email(superAdminEmail)
                .role(List.of(superAdmin,customer))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) throws DataIntegrityViolationException {

        validationUtil.validate(request);

        Role role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();

        userAccountRepository.saveAndFlush(account);
        Customer customer = Customer.builder()
                .userAccount(account)
                .emailCustomer(request.getEmail())
                .build();

        customerService.create(customer);

        return RegisterResponse.builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticated = authenticationManager.authenticate(authentication);

        UserAccount userAccount = (UserAccount) authenticated.getPrincipal();
        String token = jwtService.generateToken(userAccount);
        return LoginResponse.builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
