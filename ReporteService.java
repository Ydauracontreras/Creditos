package ar.com.ada.creditos;

import java.util.List;

import ar.com.ada.creditos.entities.ReporteCancelaciones;
import ar.com.ada.creditos.entities.ReporteCliente;
import ar.com.ada.creditos.entities.ReportePrestamos;
import ar.com.ada.creditos.managers.CancelacionManager;
import ar.com.ada.creditos.managers.PrestamoManager;

public class ReporteService {

    PrestamoManager prestamoM;
    CancelacionManager cManager;

    // Constructor
    public ReporteService(PrestamoManager prestamoM, CancelacionManager cManager) {
        this.prestamoM = prestamoM;
        this.cManager = cManager;
    }

    public void imprimirReportePrestamosTotal() {
        List<ReportePrestamos> listaPrestamo = prestamoM.generarReporteDePrestamos();

        for (ReportePrestamos rTotal : listaPrestamo) {

            System.out.println("****************Datos de Prestamos Totales******************");
            System.out.println("****************Cantidad de Prestamos: *****************");
            System.out.println(rTotal.getCantidad());
            System.out.println("****************Cantidad Total De Dinero Prestado: ******************");
            System.out.println(rTotal.getTotal());

        }
    }

    public void imprimirReportePorCliente(int clienteid) {
        List<ReporteCliente> lista = prestamoM.generarReportePrestamoCliente(clienteid);
        for (ReporteCliente r : lista) {
            System.out.println("****************Datos de Prestamo por Cliente******************");
            System.out.println("El cliente " + r.getNombre() + "\n" + " ID: " + r.getClienteId() + "\n"
                    + "tiene los siguientes datos: " + "\n" + "cantidad de Prestamos: " + r.getCantidad() + "\n"
                    + " Importe Maximo de los Prestamos: " + r.getMaximo() + "\n" + " Total Prestado:  "
                    + r.getTotal());

        }
    }

    public void imprimirReporteCancelaciones() {
        List<ReporteCancelaciones> lista = cManager.generarReporteCancelaciones();
        for (ReporteCancelaciones r : lista) {

            System.out.println("**************** Datos de Cancelaciones totales ******************");
            System.out.println("****************Cantidad de Cancelaciones: *****************");
            System.out.println(r.getCantidad());
            System.out.println("****************Cantidad Total De Dinero Cancelado: ******************");
            System.out.println(r.getTotal());
        }

    }

}