package ar.com.ada.creditos.entities;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "cancelacion")
public class Cancelacion {
    @Id
    @Column(name = "cancelacion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cancelacionId;
    @Column(name = "fecha_cancelacion")
    private Date fechaCancelacion;
    private BigDecimal importe;
    private int cuota;
    @Column(name = "estado")
    private int activado;
    @ManyToOne
    @JoinColumn(name = "prestamo_id", referencedColumnName = "prestamo_id")
    private Prestamo prestamo;

    /**
     * @return the cancelacionId
     */
    public int getCancelacionId() {
        return cancelacionId;
    }

    /**
     * @param cancelacionId the cancelacionId to set
     */
    public void setCancelacionId(int cancelacionId) {
        this.cancelacionId = cancelacionId;
    }

    /**
     * @return the fechaCancelacion
     */
    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    /**
     * @param fechaCancelacion the fechaCancelacion to set
     */
    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the cuota
     */
    public int getCuota() {
        return cuota;
    }

    /**
     * @param cuota the cuota to set
     */
    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    /**
     * @return the prestamo
     */
    public Prestamo getPrestamo() {
        return prestamo;
    }

    /**
     * @param prestamo the prestamo to set
     */
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;

    }

    /**
     * @return the activado
     */
    public int getActivado() {
        return activado;
    }

    /**
     * @param activado the activado to set
     */
    public void setActivado(int activado) {
        this.activado = activado;
    }

}
