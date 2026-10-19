package donaciones.dominio;

public class Emergencia {

    private Long id;
    private String nombre;
    private String ciudad;
    private Long meta; // dinero

    public Emergencia() {
    }

    public Emergencia(String nombre, String ciudad, Long meta) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Long getMeta() {
        return meta;
    }

    public void setMeta(Long meta) {
        this.meta = meta;
    }
}
