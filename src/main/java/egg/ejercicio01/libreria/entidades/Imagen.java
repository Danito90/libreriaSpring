package egg.ejercicio01.libreria.entidades;

import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Imagen {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String nombre;

    private String mime; // Mime type le dice al navegador el tipo de imagen

    @Lob //para avisar a la base de datos que es un tipo de dato grande
    @Basic(fetch = FetchType.LAZY) //carga perezosa, para que se cargue la foto solo cuando se pide
    private byte[] contenido;

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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    
}
