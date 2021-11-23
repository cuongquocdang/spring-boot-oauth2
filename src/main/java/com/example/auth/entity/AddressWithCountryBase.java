package com.example.auth.entity;

import com.example.auth.entity.customer.Country;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AddressWithCountryBase extends AddressBase {
    @ManyToOne
    @JoinColumn(name = "country_id")
    protected Country country;
}
