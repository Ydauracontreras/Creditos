package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.ada.creditos.entities.*;

public class PrestamoManager {

    protected SessionFactory sessionFactory;

    public void setup() {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure() // configures settings
                                                                                                  // from
                                                                                                  // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (final Exception ex) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw ex;
        }

    }

    public void exit() {
        sessionFactory.close();
    }

    public void create(final Prestamo prestamo) {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public void delete(final Prestamo prestamo) {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(prestamo);

        session.getTransaction().commit();
        session.close();
    }

    public List<Prestamo> listarPrestamos() {
        final Session session = sessionFactory.openSession();

        /// NUNCA HARCODEAR SQLs nativos en la aplicacion.
        // ESTO es solo para nivel educativo
        final Query query = session.createNativeQuery("SELECT * FROM creditos.prestamos", Prestamo.class);

        final List<Prestamo> todos = query.getResultList();

        return todos;

    }

    public List<ReporteCliente> generarReportePrestamoCliente(int clienteid) {
        Session session = sessionFactory.openSession();
        // QueryNativa
        Query queryReporteCliente = session.createNativeQuery(
                "SELECT c.cliente_id, c.nombre, count(*) cantidad, max(p.importe) maximo, sum(p.importe) total FROM cliente c inner join prestamos p on c.cliente_id = p.cliente_id WHERE c.cliente_id = ? group by c.cliente_id, c.nombre",
                ReporteCliente.class);
        queryReporteCliente.setParameter(1, clienteid);

        List<ReporteCliente> ReportePrestamosXCliente = queryReporteCliente.getResultList();

        return ReportePrestamosXCliente;

    }

    public List<ReportePrestamos> generarReporteDePrestamos() {

        Session session = sessionFactory.openSession();

        Query queryReportePrestamos = session.createNativeQuery(
                "Select count(*) cantidad, sum(p.importe) total From prestamos p", ReportePrestamos.class);

        List<ReportePrestamos> reporteDePrestamos = queryReportePrestamos.getResultList();

        return reporteDePrestamos;

    }

    public Prestamo read(final int prestamoId) {
        final Session session = sessionFactory.openSession();

        final Prestamo prestamo = session.get(Prestamo.class, prestamoId);

        session.close();

        return prestamo;
    }

}
