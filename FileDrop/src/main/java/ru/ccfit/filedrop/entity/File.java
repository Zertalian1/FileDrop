package ru.ccfit.filedrop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files", uniqueConstraints = @UniqueConstraint(columnNames = {"name","order_id"}))
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @Column
    private String path;

    @ManyToOne
    private User ownerUser;

    @Column
    private OffsetDateTime createDateTime;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
