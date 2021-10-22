package com.examplemynotes.notes.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(
                        nullable = false)
        private String title;
        @Column(
                        nullable = false)
        private String description;

        @ManyToOne(
                        cascade = CascadeType.ALL)
        @JoinColumn(
                        name = "user_id",
                        referencedColumnName = "id")

        private User user;

}
