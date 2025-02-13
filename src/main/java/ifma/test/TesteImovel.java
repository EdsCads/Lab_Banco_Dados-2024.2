package ifma.test;

import java.time.LocalDate;

import ifma.modelo.Cliente;
import ifma.modelo.Imovel;
import ifma.modelo.TipoImovel;
import ifma.repositorio.ImovelRepositorio;
import ifma.util.EMFactory;
import jakarta.persistence.EntityManager;

public class TesteImovel {
    
    public static void main(String[] args) {
        EMFactory factory = new EMFactory();
        EntityManager em = factory.getEntityManager();
        ImovelRepositorio repositorio = new ImovelRepositorio(em);

        try {
            // Iniciando transação
            em.getTransaction().begin();
            
            // Criando e persistindo clientes
            Cliente proprietario1 = criarCliente("Marquinho Leitoso", "35873568", "example@email.com");
            Cliente proprietario2 = criarCliente("Jrema Evoada", "8354843568", "example@email.com.br");
            
            em.persist(proprietario1);
            em.persist(proprietario2);

            // Criando e persistindo tipos de imóveis
            TipoImovel tipoApartamento = criarTipoImovel("Apartamento");
            TipoImovel tipoCasa = criarTipoImovel("Casa");
            
            em.persist(tipoApartamento);
            em.persist(tipoCasa);

            // Criando e persistindo imóveis
            Imovel imovel1 = criarImovel(
                "Rua das Flores, 123",
                "Centro",
                "65000-000",
                80,
                true,
                false,
                true,
                1500.0f,
                "Apartamento novo",
                tipoApartamento,
                proprietario1
            );

            Imovel imovel2 = criarImovel(
                "Av. Principal, 456",
                "Jardim",
                "65000-001",
                120,
                true,
                true,
                true,
                2500.0f,
                "Casa com quintal",
                tipoCasa,
                proprietario2
            );

            // Salvando imóveis usando o repositório
            repositorio.salvaOuAtualiza(imovel1);
            repositorio.salvaOuAtualiza(imovel2);
            
            // Commitando transação
            em.getTransaction().commit();

            // Testando listagem de imóveis
            System.out.println("\nListando todos os imóveis:");
            repositorio.todosImoveis().forEach(imovel -> {
                System.out.println("----------------------------------------");
                System.out.println("Endereço: " + imovel.getLogradouro() + ", " + imovel.getBairro());
                System.out.println("CEP: " + imovel.getCep());
                System.out.println("Tipo: " + imovel.getTipoImovel().getDescricao());
                System.out.println("Proprietário: " + imovel.getProprietario().getNome());
                System.out.println("Valor Sugerido: R$ " + imovel.getValorAluguelSugerido());
                System.out.println("----------------------------------------");
            });

            // Testando remoção
            System.out.println("\nRemovendo um imóvel...");
            em.getTransaction().begin();
            repositorio.remover(imovel1);
            em.getTransaction().commit();

            // Verificando a remoção
            System.out.println("\nListando imóveis após remoção:");
            repositorio.todosImoveis().forEach(imovel -> 
                System.out.println("Imóvel: " + imovel.getLogradouro()));
            
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

    private static Cliente criarCliente(String nome, String cpf, String email) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setDt_Nascimento(LocalDate.now().minusYears(30));
        return cliente;
    }

    private static TipoImovel criarTipoImovel(String descricao) {
        TipoImovel tipo = new TipoImovel();
        tipo.setDescricao(descricao);
        return tipo;
    }

    private static Imovel criarImovel(String logradouro, String bairro, String cep,
                                    Integer metragem, Boolean temDormitorios,
                                    Boolean temSuites, Boolean temVagasGaragem,
                                    Float valorAluguelSugerido, String obs,
                                    TipoImovel tipoImovel, Cliente proprietario) {
        Imovel imovel = new Imovel();
        imovel.setLogradouro(logradouro);
        imovel.setBairro(bairro);
        imovel.setCep(cep);
        imovel.setMetragem(metragem);
        imovel.setTemDormitorios(temDormitorios);
        imovel.setTemSuites(temSuites);
        imovel.setTemVagasGaragem(temVagasGaragem);
        imovel.setValorAluguelSugerido(valorAluguelSugerido);
        imovel.setObs(obs);
        imovel.setTipoImovel(tipoImovel);
        imovel.setProprietario(proprietario);
        return imovel;
    }
}
