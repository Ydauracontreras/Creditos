package ar.com.ada.creditos.managers;

import java.util.List;
import java.util.logging.Level;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javax.persistence.Query;

import ar.com.ada.creditos.entities.*;

public class CancelacionManager {

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

    public void create(final Cancelacion cancelacion) {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public Cancelacion read(final int prestamoId) {
        final Session session = sessionFactory.openSession();

        final Cancelacion cancelacion = session.get(Cancelacion.class, prestamoId);

        session.close();

        return cancelacion;
    }

    public void delete(final Cancelacion cancelacion) {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public void update(final Cancelacion cancelacion) {

        final Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(cancelacion);

        session.getTransaction().commit();
        session.close();
    }

    public Cancelacion readByDNI(final String dni) {
        final Session session = sessionFactory.openSession();

        final Cancelacion cancelacion = session.get(Cancelacion.class, dni);

        session.close();

        return cancelacion;
    }

    public List<ReporteCancelaciones> generarReporteCancelaciones() {

        Session session = sessionFactory.openSession();

        Query queryReporteCancelaciones = session.createNativeQuery(
                "Select count(*) cantidad, sum(c.importe) total From creditos.cancelacion c;",
                ReporteCancelaciones.class);

        List<ReporteCancelaciones> reporteCancelaciones = queryReporteCancelaciones.getResultList();

        return reporteCancelaciones;

    }

    public Cancelacion readIdCancelacion(final int idCancelacion) {
        final Session session = sessionFactory.openSession();

        final Cancelacion cancelacion = session.get(Cancelacion.class, idCancelacion);

        session.close();

        return cancelacion;
    }

    public boolean EliminarLogicamente(int idCancelacion) {
        Session session = sessionFactory.openSession();
        Query query = session.createNativeQuery(
                "Update creditos.cancelacion SET activacion = 0 where cancelacion_id = ?", Cancelacion.class);

        query.setParameter(1, idCancelacion);
        session.beginTransaction();
        int modificacion = query.executeUpdate();
        if (modificacion == 1) {
            session.getTransaction().commit();
            session.close();
            return true;
        } else {
            session.getTransaction().rollback();
            session.close();
            return false;
        }

    }

}