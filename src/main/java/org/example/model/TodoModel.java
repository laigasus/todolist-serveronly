package org.example.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long order;

    @Column(nullable = false)
    private Boolean completed;
}