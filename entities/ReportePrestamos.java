package ar.com.ada.creditos.entities;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
public class ReportePrestamos {

    @Id
    private int cantidad;
    private BigDecimal total;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}