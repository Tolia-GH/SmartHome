package com.demo.backend.databaseJPA.address;

import com.demo.backend.databaseJPA.Enum.City;
import com.demo.backend.databaseJPA.Enum.Country;
import com.demo.backend.databaseJPA.Enum.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address", schema = "smart_home")
public class AddressJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "country")
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "city")
    private City city;

    @Column(nullable = false, name = "street")
    private String street;
}

