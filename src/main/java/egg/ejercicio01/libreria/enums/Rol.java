package egg.ejercicio01.libreria.enums;

public enum Rol {
    ADMIN("ADMIN"), USUARIO("USUARIO");

    private final String valor;

    private Rol(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}


