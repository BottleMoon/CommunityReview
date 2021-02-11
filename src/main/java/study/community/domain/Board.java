package study.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;
    @Column
    private String title;
    @Column
    private String text;
    @Column
    private LocalDateTime createdTime;
    @Column
    private LocalDateTime updatedTime;
    @ManyToOne
    private User user;
}