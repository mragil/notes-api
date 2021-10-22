package com.examplemynotes.notes.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormNote {
    private String title;
    private String description;
    private Long userId;
}
