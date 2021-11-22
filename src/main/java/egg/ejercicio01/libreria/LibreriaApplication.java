package egg.ejercicio01.libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import egg.ejercicio01.libreria.servicios.UsuarioServicio;

@SpringBootApplication
public class LibreriaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(LibreriaApplication.class, args);
	}



}
