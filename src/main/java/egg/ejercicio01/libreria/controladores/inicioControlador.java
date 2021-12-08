package egg.ejercicio01.libreria.controladores;

import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/") // escucha cada ves que pongan una barra, desde que se pone la barra
public class inicioControlador {

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente");
        }
        return "login.html";
    }

    @GetMapping("/") // responde a una peticion get
    public String inicio() {
        return "index";
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

    @GetMapping("/usuario") // responde a una peticion get
    public String registro() {
        return "usuario.html";
    }

    @GetMapping("/prestamo") // responde a una peticion get
    public String prestamo() {
        return "prestamo.html";
    }

    @GetMapping("/cliente") // responde a una peticion get
    public String cliente() {
        return "cliente.html";
    }
}
