package donaciones.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "emergencias")
public class Emergencia {

    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // la bd crea el id
    private Long id;
    @Column(nullable = false, length = 150)
    private String nombre;
    @Column(nullable = false, length = 100)
    private String ciudad;
    @Column(nullable = false)
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
