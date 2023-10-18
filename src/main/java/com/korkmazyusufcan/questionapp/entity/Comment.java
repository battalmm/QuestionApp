package com.korkmazyusufcan.questionapp.entity;

import com.korkmazyusufcan.questionapp.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(columnDefinition = "text")
    private String text;

    @CreationTimestamp
    private LocalDate creationDate;

    public Comment(Post post,
                   User user,
                   String text) {
        this.post = post;
        this.user = user;
        this.text = text;
    }
    public Comment(Long id,
                   Post post,
                   User user,
                   String text) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.text = text;
    }
}
