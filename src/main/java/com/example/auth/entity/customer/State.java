package com.example.auth.entity.customer;

import com.example.auth.entity.IdBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "states")
@Getter
@Setter
public class State extends IdBase {
    @Column(nullable = false, length = 45)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
