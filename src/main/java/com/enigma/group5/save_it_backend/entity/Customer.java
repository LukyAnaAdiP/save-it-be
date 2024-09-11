package com.enigma.group5.save_it_backend.entity;

import com.enigma.group5.save_it_backend.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = ConstantTable.CUSTOMER)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true, nullable = false)
    private UserAccount userAccount;

    @OneToOne
    @JoinColumn(name = "customer_image_id", unique = true)
    private Image customerImage;

    @Column(name = "full_name_customer")
    private String fullNameCustomer;

    @Column(name = "email_customer")
    private String emailCustomer;

    @Column(name = "phone_customer")
    private String phoneCustomer;

    @Column(name = "address_customer")
    private String addressCustomer;

}
