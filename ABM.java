package ar.com.ada.creditos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import ar.com.ada.creditos.entities.*;
import ar.com.ada.creditos.excepciones.*;
import ar.com.ada.creditos.managers.*;

public class ABM {

    public static Scanner Teclado = new Scanner(System.in);

    protected ClienteManager ABMCliente = new ClienteManager();
    protected PrestamoManager ABMPrestamo = new PrestamoManager();
    protected CancelacionManager ABMCancelacion = new CancelacionManager();
    protected ReporteService ReporteService = new ReporteService(ABMPrestamo, ABMCancelacion);
    protected CancelacionService cancelacionService = new CancelacionService(ABMCancelacion, ABMPrestamo);

    public void iniciar() throws Exception {

        try {

            ABMCliente.setup();
            ABMPrestamo.setup();
            ABMCancelacion.setup();

            printOpciones();

            int opcion = Teclado.nextInt();
            Teclado.nextLine();

            while (opcion > 0) {

                switch (opcion) {
                    case 1:

                        try {
                            alta();
                        } catch (ClienteDNIException exdni) {
                            System.out.println("Error en el DNI. Indique uno valido");
                        }
                        break;

                    case 2:
                        baja();
                        break;

                    case 3:
                        modifica();
                        break;

                    case 4:
                        listar();
                        break;

                    case 5:
                        listarPorNombre();
                        break;

                    case 6:
                        altaPrestamo();

                        break;

                    case 7:
                        listarPrestamos();
                        break;

                    case 8:
                        EliminarPrestamos();
                        break;

                    case 9:
                        ReporteCliente();
                        break;

                    case 10:
                        PrestamosTotales();
                        break;

                    case 11:
                        AltaCancelacion();
                        break;

                    case 12:
                        BajaCancelacion();
                        break;

                    case 13:
                        ReporteCancelaciones();
                        break;

                    default:
                        System.out.println("La opcion no es correcta.");
                        break;

                }
                printOpciones();

                opcion = Teclado.nextInt();
                Teclado.nextLine();
            }

            // Hago un safe exit del manager
            ABMCliente.exit();

        } catch (

        Exception e) {
            // TODO: handle exception
            System.out.println("Que lindo mi sistema,se rompio mi sistema");
            throw e;
        } finally {
            System.out.println("Saliendo del sistema, bye bye...");

        }

    }

    public void alta() throws Exception {
        Cliente cliente = new Cliente();
        System.out.println("Ingrese el nombre:");
        cliente.setNombre(Teclado.nextLine());
        System.out.println("Ingrese el DNI:");
        cliente.setDni(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Ingrese la domicilio:");
        cliente.setDomicilio(Teclado.nextLine());

        System.out.println("Ingrese el Domicilio alternativo(OPCIONAL):");

        String domAlternativo = Teclado.nextLine();

        if (domAlternativo != null)
            cliente.setDomicilioAlternativo(domAlternativo);

        ABMCliente.create(cliente);

        /*
         * Si concateno el OBJETO directamente, me trae todo lo que este en el metodo
         * toString() mi recomendacion es NO usarlo para imprimir cosas en pantallas, si
         * no para loguear info Lo mejor es usar:
         * System.out.println("Cliente generada con exito.  " + cliente.getClienteId);
         */

        System.out.println("Cliente generada con exito.  " + cliente);

    }

    public void baja() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el ID de Cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {

            try {

                ABMCliente.delete(clienteEncontrado);
                System.out
                        .println("El registro del cliente " + clienteEncontrado.getClienteId() + " ha sido eliminado.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error al eliminar una cliente. Error: " + e.getCause());
            }

        }
    }

    public void bajaPorDNI() {
        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();
        System.out.println("Ingrese el DNI de Cliente:");
        String dni = Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.readByDNI(dni);

        if (clienteEncontrado == null) {
            System.out.println("Cliente no encontrado.");

        } else {
            ABMCliente.delete(clienteEncontrado);
            System.out.println("El registro del DNI " + clienteEncontrado.getDni() + " ha sido eliminado.");
        }
    }

    public void modifica() throws Exception {
        // System.out.println("Ingrese el nombre de la cliente a modificar:");
        // String n = Teclado.nextLine();

        System.out.println("Ingrese el ID de la cliente a modificar:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);

        if (clienteEncontrado != null) {

            // RECOMENDACION NO USAR toString(), esto solo es a nivel educativo.
            System.out.println(clienteEncontrado.toString() + " seleccionado para modificacion.");

            System.out.println(
                    "Elija qué dato de la cliente desea modificar: \n1: nombre, \n2: DNI, \n3: domicilio, \n4: domicilio alternativo");
            int selecper = Teclado.nextInt();

            switch (selecper) {
                case 1:
                    System.out.println("Ingrese el nuevo nombre:");
                    Teclado.nextLine();
                    clienteEncontrado.setNombre(Teclado.nextLine());

                    break;
                case 2:
                    System.out.println("Ingrese el nuevo DNI:");
                    Teclado.nextLine();
                    clienteEncontrado.setDni(Teclado.nextInt());
                    Teclado.nextLine();

                    break;
                case 3:
                    System.out.println("Ingrese el nuevo domicilio:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilio(Teclado.nextLine());

                    break;
                case 4:
                    System.out.println("Ingrese el nuevo domicilio alternativo:");
                    Teclado.nextLine();
                    clienteEncontrado.setDomicilioAlternativo(Teclado.nextLine());

                    break;

                default:
                    break;
            }

            // Teclado.nextLine();

            ABMCliente.update(clienteEncontrado);

            System.out.println("El registro de " + clienteEncontrado.getNombre() + " ha sido modificado.");

        } else {
            System.out.println("Cliente no encontrado.");
        }

    }

    public void listar() {
        List<Cliente> todos = ABMCliente.buscarTodos();
        for (Cliente c : todos) {
            mostrarCliente(c);
        }
    }

    public void listarPorNombre() {

        System.out.println("Ingrese el nombre:");
        String nombre = Teclado.nextLine();

        List<Cliente> clientes = ABMCliente.buscarPor(nombre);
        for (Cliente cliente : clientes) {
            mostrarCliente(cliente);
        }
    }

    private void mostrarPrestamo(Prestamo prestamo) {

        System.out.print(" Id: " + prestamo.getPrestamoId() + " Nombre: " + prestamo.getCliente().getNombre()
                + " Importe Prestado: " + prestamo.getImporte() + " Fecha Prestamo: " + prestamo.getFecha()
                + " Cuota de Pestamo: " + prestamo.getCuota());
        System.out.println("\n");

    }

    public void mostrarCliente(Cliente cliente) {

        System.out.print("Id: " + cliente.getClienteId() + " Nombre: " + cliente.getNombre() + " DNI: "
                + cliente.getDni() + " Domicilio: " + cliente.getDomicilio());

        if (cliente.getDomicilioAlternativo() != null)
            System.out.println(" Alternativo: " + cliente.getDomicilioAlternativo());
        else
            System.out.println();
    }

    // PRESTAMOS
    public void listarPrestamos() {
        List<Prestamo> prestamos = ABMPrestamo.listarPrestamos();
        for (Prestamo p : prestamos) {
            mostrarPrestamo(p);
        }
    }

    public void altaPrestamo() throws Exception {
        Prestamo prestamo = new Prestamo();
        System.out.println("Ingrese el ID Cliente:");
        int id = Teclado.nextInt();
        Teclado.nextLine();
        Cliente clienteEncontrado = ABMCliente.read(id);
        prestamo.setCliente(clienteEncontrado);
        System.out.println("Ingrese el importe del Prestamo:");
        prestamo.setImporte(Teclado.nextBigDecimal());
        System.out.println("Ingrese la cantidad de cuotas");
        prestamo.setCuota(Teclado.nextInt());
        Teclado.nextLine();
        System.out.println("Introduzca la fecha con formato dd/mm/yyyy");
        String fecha = Teclado.nextLine();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        String date = fecha;
        try {
            testDate = df.parse(date);
            System.out.println("Hemos creado un numero prestamo para el cliente: " + prestamo.getCliente().getNombre()
                    + ", Por un importe de: $" + prestamo.getImporte());
        } catch (Exception e) {
            System.out.println("invalid format");
        }
        prestamo.setFecha(testDate);
        prestamo.setFechaAlta(new Date());
        ABMPrestamo.create(prestamo);

    }

    public void EliminarPrestamos() {

    }

    public void ReporteCliente() {
        System.out.println("Ingrese el Id Del Cliente: ");
        int clienteid = Teclado.nextInt();
        Teclado.nextLine();
        ReporteService.imprimirReportePorCliente(clienteid);

    }

    public void PrestamosTotales() {
        ReporteService.imprimirReportePrestamosTotal();
    }

    private void ReporteCancelaciones() {
        ReporteService.imprimirReporteCancelaciones();

    }

    // CANCELACIONES

    public void AltaCancelacion() {
        System.out.println("Ingrese el ID Prestamo:");
        int prestamoId = Teclado.nextInt();
        Teclado.nextLine();
        System.out.println("Ingrese el numero de cuota");
        int cuota = Teclado.nextInt();
        Teclado.nextLine();
        System.out.println("Ingrese el importa a pagar");
        BigDecimal importe = Teclado.nextBigDecimal();
        Teclado.nextLine();
        System.out.println("Ingresa la fecha de Cancelacion:");
        System.out.println("Introduzca la fecha con formato dd/mm/yyyy");
        String fechaCancelacion = Teclado.nextLine();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date testDate = null;
        String date = fechaCancelacion;
        try {
            testDate = df.parse(date);

        } catch (Exception e) {
            System.out.println("invalid format");
        }

        cancelacionService.AltaCancelacion(prestamoId, cuota, importe, testDate);

    }

    private void BajaCancelacion() throws Exception {
        System.out.println("Ingrese el ID de la Cancelacion:");
        int cancelacionId = Teclado.nextInt();
        Teclado.nextLine();
        cancelacionService.BajaCancelacion(cancelacionId);

    }

    /*
     * private void BajaCancelacion() throws Exception { Cancelacion cancelacion =
     * new Cancelacion(); System.out.println("Ingrese el ID Prestamo:"); int
     * cancelacionId = Teclado.nextInt(); Teclado.nextLine();
     * cancelacion.setCancelacionId(cancelacionId); cancelacion.setActivado(0);
     * 
     * System.out.println("Se borro la cancelacion de: " +
     * cancelacion.getPrestamo().getCliente().getNombre() + "con fecha" +
     * cancelacion.getFechaCancelacion()); }
     */
    // Listado de opciones
    public static void printOpciones() {
        System.out.println("=======================================");
        System.out.println("");
        System.out.println("1. Para agregar un cliente.");
        System.out.println("2. Para eliminar un cliente.");
        System.out.println("3. Para modificar un cliente.");
        System.out.println("4. Para ver el listado.");
        System.out.println("5. Buscar un cliente por nombre especifico(SQL Injection)).");
        System.out.println("6. Para agregar un nuevo prestamo a un Cliente.");
        System.out.println("7. Ver Listado de prestamos Otorgados.");
        System.out.println("8. Eliminar un prestamo.");
        System.out.println(
                "9. Reporte 1: Por cliente, saber cuantos prestamos tiene, cual el importe mas alto y cuanto en total de prestamos.");
        System.out.println("10. Reporte 2: Totales de prestamos y cuanta plata es.");
        System.out.println("11. Crear una Cancelacion");
        System.out.println("12. Eliminar una Cancelacion");
        System.out.println("13. Reportar las Cancelaciones");
        System.out.println("0. Para terminar.");
        System.out.println("");
        System.out.println("=======================================");
    }
}