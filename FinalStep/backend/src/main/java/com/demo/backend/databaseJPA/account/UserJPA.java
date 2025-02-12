package com.demo.backend.databaseJPA.account;

import com.demo.backend.databaseJPA.Enum.Gender;
import com.demo.backend.databaseJPA.Enum.PostgreSQLEnumType;
import com.demo.backend.databaseJPA.ListUserHouseJPA;
import com.demo.backend.databaseJPA.contact.ContactJPA;
import com.demo.backend.databaseJPA.house.HouseJPA;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user", schema = "smart_home")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class UserJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name="username")
    private String username;
    @Column(nullable = false,name="password")
    private String password;
    @Column(nullable = false,name="age")
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "user_id")
    private ContactJPA contactJPA;

    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private List<ListUserHouseJPA> listUserHouseJPAS;

//    @ManyToMany
//    @JoinTable(
//            name = "list_user_house",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "house_id")
//    )
//    private List<HouseJPA> houses;
}
