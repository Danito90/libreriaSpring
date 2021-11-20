package egg.ejercicio01.libreria.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

@Entity
public class Usuario {
    
    @Id
    private String id;

    @Size(min = 1, max = 20, message = "Debe tener de 1 a 20 caracteres")
    @NotEmpty(message = "Debes especificar el usuario")
    @Column(nullable = false)
    private String usuario;

    @Size(min = 8, message = "Debe tener un minimo de 8 caracteres")
    @NotEmpty(message = "Debes especificar la contraseña")
    @Column(nullable = false)
    private String password;

    @Email(message = "Debes especificar el mail")
    @Column(nullable = false)
    private String mail;

    @NotNull(message = "Debes especificar el estado")
    @Column(nullable = false)
    private Boolean alta;

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
    
}
