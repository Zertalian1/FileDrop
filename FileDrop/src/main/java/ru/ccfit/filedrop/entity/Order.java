package ru.ccfit.filedrop.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.ccfit.filedrop.enumeration.Status;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    @Column
    private OffsetDateTime createDateTime;

    @Column
    private OffsetDateTime storeUpTo;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files;
}
