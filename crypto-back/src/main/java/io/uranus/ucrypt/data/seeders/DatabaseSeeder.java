package io.uranus.ucrypt.data.seeders;

import io.uranus.ucrypt.data.entities.Role;
import io.uranus.ucrypt.data.entities.User;
import io.uranus.ucrypt.data.repositories.RoleRepository;
import io.uranus.ucrypt.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.uranus.ucrypt.data.entities.enums.UserStatus.ACTIVE;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Main Account
    @Value("${main-account.email}")
    private String mainAccEmail;
    @Value("${main-account.password}")
    private String mainAccPassword;

    // Employee 1
    @Value("${seed.employee1.name}")
    private String employee1Name;
    @Value("${seed.employee1.email}")
    private String employee1Email;
    @Value("${seed.employee1.password}")
    private String employee1Password;

    // Employee 2
    @Value("${seed.employee2.name}")
    private String employee2Name;
    @Value("${seed.employee2.email}")
    private String employee2Email;
    @Value("${seed.employee2.password}")
    private String employee2Password;

    // Employee 3
    @Value("${seed.employee3.name}")
    private String employee3Name;
    @Value("${seed.employee3.email}")
    private String employee3Email;
    @Value("${seed.employee3.password}")
    private String employee3Password;

    // Employee 4
    @Value("${seed.employee4.name}")
    private String employee4Name;
    @Value("${seed.employee4.email}")
    private String employee4Email;
    @Value("${seed.employee4.password}")
    private String employee4Password;

    // Employee 5
    @Value("${seed.employee5.name}")
    private String employee5Name;
    @Value("${seed.employee5.email}")
    private String employee5Email;
    @Value("${seed.employee5.password}")
    private String employee5Password;

    // Employee 6
    @Value("${seed.employee6.name}")
    private String employee6Name;
    @Value("${seed.employee6.email}")
    private String employee6Email;
    @Value("${seed.employee6.password}")
    private String employee6Password;

    // Employee 7
    @Value("${seed.employee7.name}")
    private String employee7Name;
    @Value("${seed.employee7.email}")
    private String employee7Email;
    @Value("${seed.employee7.password}")
    private String employee7Password;


    @EventListener
    @Transactional
    public void seed(final ContextRefreshedEvent event) {
        this.seedMainUsers();
    }

    private void seedMainUsers() {
        // Seed Main Admin User
        if (!this.userRepository.existsByEmail(this.mainAccEmail)) {
            final var adminRole = this.roleRepository.findByName(Role.RoleProperty.ADMIN.getName()).orElseThrow();
            this.userRepository.save(User.builder()
                    .name("Admin").email(this.mainAccEmail)
                    .password(this.passwordEncoder.encode(this.mainAccPassword))
                    .role(adminRole).status(ACTIVE).build());
        }

        final var employeeRole = this.roleRepository.findByName(Role.RoleProperty.EMPLOYEE.getName()).orElseThrow();

        // Seed Employee 1
        if (!this.userRepository.existsByEmail(this.employee1Email)) {
            this.userRepository.save(User.builder().name(this.employee1Name).email(this.employee1Email)
                    .password(this.passwordEncoder.encode(this.employee1Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 2
        if (!this.userRepository.existsByEmail(this.employee2Email)) {
            this.userRepository.save(User.builder().name(this.employee2Name).email(this.employee2Email)
                    .password(this.passwordEncoder.encode(this.employee2Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 3
        if (!this.userRepository.existsByEmail(this.employee3Email)) {
            this.userRepository.save(User.builder().name(this.employee3Name).email(this.employee3Email)
                    .password(this.passwordEncoder.encode(this.employee3Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 4
        if (!this.userRepository.existsByEmail(this.employee4Email)) {
            this.userRepository.save(User.builder().name(this.employee4Name).email(this.employee4Email)
                    .password(this.passwordEncoder.encode(this.employee4Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 5
        if (!this.userRepository.existsByEmail(this.employee5Email)) {
            this.userRepository.save(User.builder().name(this.employee5Name).email(this.employee5Email)
                    .password(this.passwordEncoder.encode(this.employee5Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 6
        if (!this.userRepository.existsByEmail(this.employee6Email)) {
            this.userRepository.save(User.builder().name(this.employee6Name).email(this.employee6Email)
                    .password(this.passwordEncoder.encode(this.employee6Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
        // Seed Employee 7
        if (!this.userRepository.existsByEmail(this.employee7Email)) {
            this.userRepository.save(User.builder().name(this.employee7Name).email(this.employee7Email)
                    .password(this.passwordEncoder.encode(this.employee7Password))
                    .role(employeeRole).status(ACTIVE).build());
        }
    }
}