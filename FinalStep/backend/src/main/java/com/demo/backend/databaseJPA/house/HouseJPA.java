package com.demo.backend.databaseJPA.house;

import com.demo.backend.databaseJPA.Enum.HouseType;
import com.demo.backend.databaseJPA.ListUserHouseJPA;
import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.address.AddressJPA;
import com.demo.backend.databaseJPA.room.RoomJPA;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "house", schema = "smart_home")
public class HouseJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressJPA addressJPA;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private HouseType houseType;

    @OneToMany
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    private List<RoomJPA> roomJPAList;

//    @ManyToMany(mappedBy = "houses")
//    private List<UserJPA> userJPAList;
}

