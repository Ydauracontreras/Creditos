package ar.com.ada.creditos;

import java.math.BigDecimal;
import java.util.Date;

import ar.com.ada.creditos.entities.Cancelacion;
import ar.com.ada.creditos.managers.CancelacionManager;
import ar.com.ada.creditos.managers.PrestamoManager;

public class CancelacionService {

    CancelacionManager cancelacionM;
    PrestamoManager prestamoM;

    // Constructor
    public CancelacionService(CancelacionManager cancelacionM, PrestamoManager prestamoM) {
        this.cancelacionM = cancelacionM;
        this.prestamoM = prestamoM;
    }

    public CancelacionService(CancelacionManager cancelacionM) {
        this.cancelacionM = cancelacionM;
    }

    public Cancelacion AltaCancelacion(int prestamoId, int cuota, BigDecimal importe, Date fechaCancelacion) {
        Cancelacion cancelacion = new Cancelacion();
        cancelacion.setPrestamo(prestamoM.read(prestamoId));
        cancelacion.setCuota(cuota);
        cancelacion.setImporte(importe);
        cancelacion.setFechaCancelacion(fechaCancelacion);

        cancelacionM.create(cancelacion);
        System.out.println("Hemos creado una cancelacion para el prestamo del cliente: "
                + cancelacion.getPrestamo().getCliente().getNombre() + ", Por un importe de: $"
                + cancelacion.getImporte());
        return cancelacion;

    }

    public void BajaCancelacion(int idCancelacion) {
        Cancelacion cancelacion = new Cancelacion();
        cancelacionM.readIdCancelacion(idCancelacion);

        if (cancelacionM.EliminarLogicamente(idCancelacion)) {
            System.out.println("Hemos desactivado una cancelacion para el prestamo del cliente: " + idCancelacion);

        } else {

            System.out.println("Error");
        }

    }

}
