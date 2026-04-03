package com.example.app.song.domain;

import com.example.app.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Song extends BaseEntity {

    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer durationSeconds;
}
