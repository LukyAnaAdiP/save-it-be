package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import com.enigma.group5.save_it_backend.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.USER_ROLE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

