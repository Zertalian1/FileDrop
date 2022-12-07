package ru.ccfit.filedrop.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.ccfit.filedrop.enumeration.Role;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String phone;

    @Column
    private String password;
}
