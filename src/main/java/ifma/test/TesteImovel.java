package ifma.test;

import java.time.LocalDate;

import ifma.modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TesteImovel {
    
    public static void main(String[] args) {
        LocalDate dataDeNascer = LocalDate.of(2001,5,11);

        Cliente cliente = new Cliente();
        cliente.setCpf("1111111111");
        cliente.setNome("Jose");
        cliente.setDt_Nascimento(dataDeNascer);

        dataDeNascer = LocalDate.of(2001,5,11);
        Cliente cliente2 = new Cliente();
        cliente2.setCpf("222222222");
        cliente2.setNome("Marcio");
        cliente2.setDt_Nascimento(dataDeNascer);

        dataDeNascer = LocalDate.of(2001,5,11);
        Cliente cliente3 = new Cliente();
        cliente3.setCpf("333333333");
        cliente3.setNome("Fernanda");
        cliente3.setDt_Nascimento(dataDeNascer);
        cliente3.setEmail("email@example");
        dataDeNascer = LocalDate.of(2001,5,11);


        
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
