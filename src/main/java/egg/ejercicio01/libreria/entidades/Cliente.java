package egg.ejercicio01.libreria.entidades;

import javax.persistence.Entity;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(min = 8, max = 8, message = "Debe tener 8 caracteres")
    @NotEmpty(message = "Debes especificar el documento")
    @Column(nullable = false)
    private String documento;

    @Size(min = 1, max = 70, message = "Debe tener de 1 a 70 caracteres")
    @NotEmpty(message = "Debes especificar el nombre")
    @Column(nullable = false)
    private String nombre;

    @Size(min = 1, max = 70, message = "Debe tener de 1 a 70 caracteres")
    @NotEmpty(message = "Debes especificar el apellido")
    @Column(nullable = false)
    private String apellido;

    @Size(min = 10, max = 10, message = "Debe tener de 10 caracteres")
    @NotEmpty(message = "Debes especificar el telefono")
    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private Boolean alta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", documento=" + documento + ", nombre=" + nombre + ", apellido=" + apellido
                + ", telefono=" + telefono + ", alta=" + alta + '}';
    }

}
