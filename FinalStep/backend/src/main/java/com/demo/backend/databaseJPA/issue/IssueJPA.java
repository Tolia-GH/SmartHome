package com.demo.backend.databaseJPA.issue;

import com.demo.backend.databaseJPA.Enum.ProblemType;
import com.demo.backend.databaseJPA.account.UserJPA;
import com.demo.backend.databaseJPA.supporter.SupporterJPA;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "issue", schema = "smart_home")
public class IssueJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserJPA user;

    @ManyToOne
    @JoinColumn(name = "supporter_id", referencedColumnName = "id", nullable = false)
    private SupporterJPA supporter;

    @Column(nullable = false, name = "is_finished")
    private Boolean isFinished;

    @Column(nullable = false, name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private ProblemType issueType;

    @Column(nullable = false, name = "create_time")
    private LocalDate createTime;
}
