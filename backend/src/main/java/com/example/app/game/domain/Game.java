package com.example.app.game.domain;

import com.example.app.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game extends BaseEntity {

    private String title;
    private String genre;
    private String platform;
    private String developer;
    private Integer releaseYear;

    @Column(columnDefinition = "TEXT")
    private String description;
}
