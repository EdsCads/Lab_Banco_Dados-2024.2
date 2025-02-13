package ifma;

import java.time.LocalDate;

import ifma.modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        LocalDate dataDeNascer = LocalDate.of(2001,5,11);

        Cliente cliente = new Cliente();
        cliente.setCpf("1111111111");
        cliente.setNome("Jose");

        System.out.println("Abrindo e persinstindo");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aluguelImovel");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        
        

        

        entityManager.close();
        emf.close();

        
        System.out.println("Cabou");
    }
}
