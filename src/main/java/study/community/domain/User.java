package study.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column
    private String id;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private LocalDateTime createDate;
}
