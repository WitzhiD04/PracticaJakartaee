package donaciones.dominio;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "donaciones")
public class Donaciones {

    @Id // llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // la bd crea el id
    private Long id;

    @Column(nullable = false, length = 150)
    private String donante;

    @Column(nullable = false)
    private Long monto;

    @Column(nullable = false)
    private LocalDateTime fecha;


    @ManyToOne
    @JoinColumn(name = "emergencia_id", nullable = false)
    private Emergencia emergencia;

    public Donaciones() {

    }

    public Donaciones(String donante, Long monto, LocalDateTime fecha) {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDonante() {
        return donante;
    }

    public void setDonante(String donante) {
        this.donante = donante;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime hora) {
        this.fecha = hora;
    }

    public Emergencia getEmergencia() {
        return emergencia;
    }

    public void setEmergencia(Emergencia emergencia) {
        this.emergencia = emergencia;
    }
}
