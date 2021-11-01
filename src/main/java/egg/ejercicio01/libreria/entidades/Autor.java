package egg.ejercicio01.libreria.entidades;

import java.util.List;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Autor {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean alta;

    // se crea una llave foranea en la otra tabla (libro), pero nose genera una
    // tabla intermedia
    // el mappedBy va donde no queremos que se genere la llave foranea
    @OneToMany(mappedBy = "autor") // un autor puede tener muchos libros
    private List<Libro> libro; // si no coloco el mappedby, se crea la tabla intermedia se llama libro_autor
    // dos columnas, libro_id y autor_id

    public Autor() {
    }

    public Autor(String id, String nombre, Boolean alta) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "Autor{" + "id=" + id + ", nombre=" + nombre + ", alta=" + alta + '}';
    }

}
