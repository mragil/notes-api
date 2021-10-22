package com.examplemynotes.notes;

import java.util.ArrayList;
import com.examplemynotes.notes.models.FormNote;
import com.examplemynotes.notes.models.Role;
import com.examplemynotes.notes.models.User;
import com.examplemynotes.notes.services.NoteService;
import com.examplemynotes.notes.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, NoteService noteService) {
		return args -> {

			Role role_admin = new Role(null, "ROLE_ADMIN");
			Role role_user = new Role(null, "ROLE_USER");

			userService.saveRole(role_admin);
			userService.saveRole(role_user);

			User _mragil = new User(null, "Muhammad Ragil", "mragil", "1234", new ArrayList<>());
			User _adelard =
					new User(null, "Adelard Jibril", "adelard31", "1234", new ArrayList<>());
			User _zeeshan =
					new User(null, "Muhammad Zeeshan", "zeeshan10", "1234", new ArrayList<>());
			User _kanaya = new User(null, "Adele Kanaya", "adelekanaya", "1234", new ArrayList<>());

			userService.saveUser(_mragil);
			User adelard = userService.saveUser(_adelard);
			User zeeshan = userService.saveUser(_zeeshan);
			User kanaya = userService.saveUser(_kanaya);

			userService.addRoleToUser("mragil", "ROLE_ADMIN");
			userService.addRoleToUser("adelard31", "ROLE_USER");
			userService.addRoleToUser("zeeshan10", "ROLE_USER");
			userService.addRoleToUser("adelekanaya", "ROLE_USER");


			FormNote formNote =
					new FormNote("Adelard - Note1", "Desc. Adelard Note1", adelard.getId());
			FormNote formNote2 =
					new FormNote("Adelard - Note2", "Desc. Adelard Note2", adelard.getId());
			FormNote formNote3 =
					new FormNote("Adelard - Note3", "Desc. Adelard Note3", adelard.getId());
			FormNote formNote4 =
					new FormNote("Adelard - Note4", "Desc. Adelard Note4", adelard.getId());
			FormNote formNote5 =
					new FormNote("Adelard - Note5", "Desc. Adelard Note5", adelard.getId());
			FormNote formNote6 =
					new FormNote("Adelard - Note6", "Desc. Adelard Note6", adelard.getId());
			FormNote formNote7 =
					new FormNote("Adelard - Note7", "Desc. Adelard Note7", adelard.getId());
			FormNote formNote8 =
					new FormNote("Adelard - Note8", "Desc. Adelard Note8", adelard.getId());
			FormNote formNote9 =
					new FormNote("Adelard - Note9", "Desc. Adelard Note9", adelard.getId());
			FormNote formNote10 =
					new FormNote("Adelard - Note10", "Desc. Adelard Note10", adelard.getId());
			FormNote formNote11 =
					new FormNote("Adelard - Note11", "Desc. Adelard Note11", adelard.getId());
			FormNote formNote12 =
					new FormNote("Adelard - Note12", "Desc. Adelard Note12", adelard.getId());

			FormNote zNote = new FormNote("Zeeshan - Note", "Desc. Zeeshan Note", zeeshan.getId());
			FormNote zNote2 =
					new FormNote("Zeeshan - Note2", "Desc. Zeeshan Note2", zeeshan.getId());
			FormNote zNote3 =
					new FormNote("Zeeshan - Note3", "Desc. Zeeshan Note3", zeeshan.getId());

			FormNote kNote = new FormNote("Kanaya - Note", "Desc. Kanaya Note", kanaya.getId());

			noteService.saveNote(formNote);
			noteService.saveNote(formNote2);
			noteService.saveNote(formNote3);
			noteService.saveNote(formNote4);
			noteService.saveNote(formNote5);
			noteService.saveNote(formNote6);
			noteService.saveNote(formNote7);
			noteService.saveNote(formNote8);
			noteService.saveNote(formNote9);
			noteService.saveNote(formNote10);
			noteService.saveNote(formNote11);
			noteService.saveNote(formNote12);

			noteService.saveNote(zNote);
			noteService.saveNote(zNote2);
			noteService.saveNote(zNote3);

			noteService.saveNote(kNote);



		};
	}

}
