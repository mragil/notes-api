package com.examplemynotes.notes.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users", uniqueConstraints = @UniqueConstraint(
                name = "username_unique",
                columnNames = "username"))
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column(
                        nullable = false)
        private String name;

        @Column(
                        nullable = false)
        private String username;

        @Column(
                        nullable = false)
        @JsonIgnore
        private String password;

        @JsonIgnore
        @ManyToMany(fetch = FetchType.EAGER)
        private Collection<Role> roles = new ArrayList<>();



}
