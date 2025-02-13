package ifma.test;

import java.time.LocalDate;

import org.hibernate.service.internal.ServiceProxyGenerationException;

import ifma.modelo.Profissionais;
import ifma.modelo.ServicosImovel;
import ifma.repositorio.ImovelRepositorio;
import ifma.repositorio.ProfissionaisRepositorio;
import ifma.repositorio.ServicosImovelRepositorio;
import ifma.util.EMFactory;
import jakarta.persistence.EntityManager;

public class TesteServicoImovel {

    public static void main(String[] args) {
        EMFactory factory = new EMFactory();
        EntityManager em = factory.getEntityManager();
        ProfissionaisRepositorio profisisonalRepo = new ProfissionaisRepositorio(em);
        ImovelRepositorio imovelRepo = new ImovelRepositorio(em);
        ServicosImovelRepositorio servicoRepo = new ServicosImovelRepositorio(em);
        try {
            // Iniciando transação
            em.getTransaction().begin();

            // Criando e persistindo Profissionaiss
            Profissionais profissional1 = new Profissionais();
            profissional1.setNome("Marquinho Carinhoso");
            profissional1.setProfissao("Corretor de Imoveis");
            profissional1.setObs("Educada, Sabe negociar um bom preço");
            profissional1.setTelefone1("98563214");

            Profissionais profissional2 = new Profissionais();
            profissional2.setNome("Luanda Evoada");
            profissional2.setProfissao("Encanador");
            profissional2.setObs("Educado, ja vendeu 3 terrenos");
            profissional2.setTelefone1("321654987");

            em.persist(profissional1);
            em.persist(profissional2);

            //Criando Servico de Imovel

            ServicosImovel servico1 = new ServicosImovel();
            servico1.setDataServico(LocalDate.now().plusDays(7));
            servico1.setImovel(imovelRepo.buscaPorId(1));
            servico1.setProfissionais(profissional2);
            servico1.setValorTotal(340.90f);

            // Commitando transação
            em.getTransaction().commit();

            // Testando listagem de imóveis
            System.out.println("\nListando todos os Serviços:");

ServicosImovel servicoEscolhido = servicoRepo.buscaPorId(1);

            System.out.println("Data do Servico: "+servicoEscolhido.getDataServico()+"\nProfissional Encarregado: "+servicoEscolhido.getProfissionais()+"\nPropriedade: "+servicoEscolhido.getImovel().getObs());

            // Testando remoção
            System.out.println("\nRemovendo um Profissional...");

            profisisonalRepo.remover(profissional2);

            em.getTransaction().commit();


        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            factory.close();
        }
    }

}
