package ifma.test;

import ifma.modelo.Cliente;
import ifma.modelo.Imovel;
import ifma.modelo.TipoImovel;
import ifma.repositorio.ClienteRepositorio;
import ifma.repositorio.ImovelRepositorio;
import ifma.util.EMFactory;
import jakarta.persistence.EntityManager;

public class TesteImovel {
    
    public static void main(String[] args) {
        EMFactory factory = new EMFactory();
        EntityManager em = factory.getEntityManager();
        ImovelRepositorio repositorio = new ImovelRepositorio(em);
        ClienteRepositorio clienteRepo = new ClienteRepositorio(em); 
        try {
            // Iniciando transação
            em.getTransaction().begin();
            
            // Criando e persistindo clientes
            Cliente proprietario1 = new Cliente();
            proprietario1.setNome("Marquinho Leitoso");
            proprietario1.setCpf("35873568");
            proprietario1.setEmail("example@email.com");

            Cliente proprietario2 = new Cliente();
            proprietario2.setNome("Jurema Evoada");
            proprietario2.setCpf("8354843568");
            proprietario2.setEmail("example@email.com.br");

            clienteRepo.salvaOuAtualiza(proprietario1);
            clienteRepo.salvaOuAtualiza(proprietario2);

            // Criando e persistindo tipos de imóveis
            TipoImovel tipoApartamento = new TipoImovel();
            tipoApartamento.setDescricao("Apartamento");
            TipoImovel tipoCasa = new TipoImovel();
            tipoCasa.setDescricao("Casa");
            
            em.persist(tipoApartamento);
            em.persist(tipoCasa);

            // Criando e persistindo imóveis

            Imovel imovel1 = new Imovel();
            imovel1.setLogradouro("Rua das Margaridas");
            imovel1.setBairro("Florizal");
            imovel1.setCep("6598-098");
            imovel1.setMetragem(320);
            imovel1.setTemDormitorios(true);
            imovel1.setTemSuites(false);
            imovel1.setTemVagasGaragem(true);
            imovel1.setValorAluguelSugerido(3560.0f);
            imovel1.setObs("Em frente ao Jardims");
            imovel1.setTipoImovel(tipoApartamento);
            imovel1.setProprietario(clienteRepo.buscaPorId(1));
            
            Imovel imovel2 = new Imovel();
            imovel2.setLogradouro("Av. Principal, 456");
            imovel2.setBairro("Jardim");
            imovel2.setCep("65000-001");
            imovel2.setMetragem(318);
            imovel2.setTemDormitorios(true);
            imovel2.setTemSuites(true);
            imovel2.setTemVagasGaragem(true);
            imovel2.setValorAluguelSugerido(2500.0f);
            imovel2.setObs("Casa com quintal");
            imovel2.setTipoImovel(tipoCasa);
            imovel2.setProprietario(proprietario2);
                
            // Salvando imóveis usando o repositório
            repositorio.salvaOuAtualiza(imovel1);
            repositorio.salvaOuAtualiza(imovel2);
            
            // Commitando transação
            em.getTransaction().commit();

            // Testando listagem de imóveis
            System.out.println("\nListando todos os imóveis:");

            for(Imovel imovel : repositorio.todosImoveis()){
                System.out.println("----------------------------------------");
                System.out.println("Endereço: " + imovel.getLogradouro() + ", " + imovel.getBairro());
                System.out.println("CEP: " + imovel.getCep());
                System.out.println("Tipo: " + imovel.getTipoImovel().getDescricao());
                System.out.println("Proprietário: " + imovel.getProprietario().getNome());
                System.out.println("Valor Sugerido: R$ " + imovel.getValorAluguelSugerido());
                System.out.println("----------------------------------------");
            }

            // Testando remoção
            System.out.println("\nRemovendo um imóvel...");
            em.getTransaction().begin();
            repositorio.remover(imovel1);
            em.getTransaction().commit();

            // Verificando a remoção
            System.out.println("\nListando imóveis após remoção:");
            for(Imovel imovel : repositorio.todosImoveis()){
                System.out.println("----------------------------------------");
                System.out.println("Endereço: " + imovel.getLogradouro() + ", " + imovel.getBairro());
                System.out.println("CEP: " + imovel.getCep());
                System.out.println("Tipo: " + imovel.getTipoImovel().getDescricao());
                System.out.println("Proprietário: " + imovel.getProprietario().getNome());
                System.out.println("Valor Sugerido: R$ " + imovel.getValorAluguelSugerido());
                System.out.println("----------------------------------------");
            }
            
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
