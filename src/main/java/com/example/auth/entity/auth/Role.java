package com.example.auth.entity.auth;

import com.example.auth.entity.AuditBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role extends AuditBase {
    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false, unique = true)
    private ManagementType name;

    @Column(length = 150, nullable = false)
    private String description;
}
