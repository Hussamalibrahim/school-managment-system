package com.SchoolManagementSystem.System.entity.library;

import com.SchoolManagementSystem.System.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "library_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryBook extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @Column(nullable = false)
    private String title;

    private String author;

    @Column(unique = true)
    private String isbn;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

}