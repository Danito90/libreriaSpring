package egg.ejercicio01.libreria.controladores;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/") // escucha cada ves que pongan una barra, desde que se pone la barra
public class inicioControlador {

    @GetMapping("/") // responde a una peticion get
    public String inicio() {
        return "index.html";
    }

    @GetMapping("/libro") // responde a una peticion get
    public String libro() {
        return "libro.html";
    }

    @GetMapping("/autor") // responde a una peticion get
    public String autor() {
        return "autor.html";
    }

    @GetMapping("/editorial") // responde a una peticion get
    public String editorial() {
        return "editorial.html";
    }

    @GetMapping("/registro") // responde a una peticion get
    public String registro() {
        return "registro.html";
    }
}
