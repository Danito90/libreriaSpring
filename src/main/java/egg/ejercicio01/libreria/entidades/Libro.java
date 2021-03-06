
package egg.ejercicio01.libreria.entidades;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Libro {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String Id;

    @Min(1)
    @Max(99999999)
    @NotNull(message = "Debes especificar el isbn")
    @Column(nullable = false)
    private Long isbn;

    @Size(min = 1, max = 100, message = "Debe tener de 1 a 100 caracteres")
    @NotEmpty(message = "Debes especificar el titulo")
    @Column(nullable = false)
    private String titulo;

    @Min(1900)
    @Max(2099)
    @NotNull(message = "Debes especificar el año")
    @Column(nullable = false)
    private Integer anio;

    @NotNull(message = "Debes especificar la cantidad de ejemplares")
    @Column(nullable = false)
    private Integer ejemplares;

    @NotNull(message = "Debes especificar la cantidad de ejemplares prestados")
    @Column(nullable = false)
    private Integer ejemplaresPrestados;

    // @NotNull(message = "Debes especificar la cantidad de ejemplares restantes")
    @Column(nullable = false)
    private Integer ejemplaresRestantes;

    @NotNull(message = "Debes especificar el estado")
    @Column(nullable = false)
    private Boolean alta;

    @ManyToOne
    private Autor autor; // muchos libros pueden pertenecer a un autor
    // Si trabajo unidireccional la relacion, se crea la llave foranea aca... si es
    // bidireccional, leer la relacion en autor

    @ManyToOne
    private Editorial editorial;

    public Libro() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getEjemplaresPrestados() {
        return ejemplaresPrestados;
    }

    public void setEjemplaresPrestados(Integer ejemplaresPrestados) {
        this.ejemplaresPrestados = ejemplaresPrestados;
    }

    public Integer getEjemplaresRestantes() {
        return ejemplaresRestantes;
    }

    public void setEjemplaresRestantes(Integer ejemplaresRestantes) {
        this.ejemplaresRestantes = ejemplaresRestantes;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Libro{" + "Id=" + Id + ", isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", ejemplares="
                + ejemplares + ", ejemplaresPrestados=" + ejemplaresPrestados + ", ejemplaresRestantes="
                + ejemplaresRestantes + ", alta=" + alta + ", autor=" + autor + ", editorial=" + editorial + '}';
    }

}
