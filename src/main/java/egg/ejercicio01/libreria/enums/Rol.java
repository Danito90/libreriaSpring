package egg.ejercicio01.libreria.enums;

public enum Rol {
    ADMIN(1,"ADMIN"), USUARIO(2,"USUARIO");
    private Integer codigo;
    private String valor;

    private Rol(Integer codigo, String valor) {
        this.codigo = codigo;
        this.valor = valor;
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public String getValor() {
        return valor;
    }
}


