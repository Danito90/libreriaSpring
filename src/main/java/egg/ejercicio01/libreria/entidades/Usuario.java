package egg.ejercicio01.libreria.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

import org.hibernate.annotations.GenericGenerator;

import egg.ejercicio01.libreria.enums.Rol;



@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Size(min = 1, max = 20, message = "Debe tener de 1 a 20 caracteres")
    @NotEmpty(message = "Debes especificar el usuario")
    @Column(nullable = false,unique = true)
    private String usuario;

    @Size(min = 8, message = "Debe tener un minimo de 8 caracteres")
    @NotEmpty(message = "Debes especificar la contraseña")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Debes especificar el mail")
    @Email(message = "Debes especificar un mail valido")
    @Column(nullable = false,unique = true)
    private String mail;

    @NotNull(message = "Debes especificar el estado")
    @Column(nullable = false)
    private Boolean alta;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @OneToOne
    private Imagen imagenPerfil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Imagen getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(Imagen imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
}
