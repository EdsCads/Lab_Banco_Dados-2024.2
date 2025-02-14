package ifma.test;
//Testando funcionalidades de aluguel, Cliente, locação e imóvel
import java.time.LocalDate;

import ifma.modelo.Aluguel;
import ifma.modelo.Cliente;
import ifma.modelo.Imovel;
import ifma.modelo.Locacao;
import ifma.modelo.TipoImovel;
import ifma.repositorio.AluguelRepositorio;
import ifma.repositorio.ClienteRepositorio;
import ifma.repositorio.ImovelRepositorio;
import ifma.repositorio.LocacaoRepository;
import ifma.util.EMFactory;
import jakarta.persistence.EntityManager;

public class TesteAluguel {
    
    public static void main(String[] args) {
        EMFactory factory = new EMFactory();
        EntityManager em = factory.getEntityManager();
        
        ClienteRepositorio clienteRepo = new ClienteRepositorio(em);
        ImovelRepositorio imovelRepo = new ImovelRepositorio(em);
        LocacaoRepository locacaoRepo = new LocacaoRepository(em);
        AluguelRepositorio aluguelRepo = new AluguelRepositorio(em);
        try {
            em.getTransaction().begin();

            // Criando proprietário
            Cliente proprietario = new Cliente();
            proprietario.setNome("João da Silva");
            proprietario.setCpf("12345678901");
            proprietario.setTelefone("98988887777");
            proprietario.setEmail("joao@email.com");
            proprietario.setDt_Nascimento(LocalDate.of(1980, 5, 15));

            // Criando inquilino
            Cliente inquilino = new Cliente();
            inquilino.setNome("Maria Souza");
            inquilino.setCpf("98765432101");
            inquilino.setTelefone("98999996666");
            inquilino.setEmail("maria@email.com");
            inquilino.setDt_Nascimento(LocalDate.of(1990, 8, 25));

            // Salvando clientes
            clienteRepo.salvaOuAtualiza(proprietario);
            clienteRepo.salvaOuAtualiza(inquilino);

            // Criando imóvel
            Imovel imovel = new Imovel();
            imovel.setLogradouro("Rua das Palmeiras, 123");
            imovel.setBairro("Centro");
            imovel.setCep("65000-000");
            imovel.setMetragem(80);
            imovel.setTemDormitorios(true);
            imovel.setTemSuites(true);
            imovel.setTemVagasGaragem(true);
            imovel.setValorAluguelSugerido(1500.0f);
            imovel.setObs("Apartamento bem localizado");
            imovel.setTipoImovel(TipoImovel.APARTAMENTO);
            imovel.setProprietario(proprietario);

            imovelRepo.salvaOuAtualiza(imovel);

            // Criando locação
            Locacao locacao = new Locacao();
            locacao.setValorAluguel(1500.0f);
            locacao.setDiaVencimento(10);
            locacao.setAtivo(true);
            locacao.setDataInicio(LocalDate.now());
            locacao.setPercentualMulta(0.1f);
            locacao.setObs("Contrato inicial de 12 meses");
            locacao.setImovel(imovel);
            locacao.setInquilino(inquilino);

            locacaoRepo.salvaOuAtualiza(locacao);

            // Criando alugueis para a locação
            // Criando primeiro mês de aluguel
            Aluguel aluguel1 = new Aluguel();
            aluguel1.setDataVencimento(LocalDate.now().plusMonths(1));
            aluguel1.setValorPago(1500.0f);
            aluguel1.setDataPagamento(LocalDate.now());
            aluguel1.setObs("Primeiro mês de aluguel");
            aluguel1.setLocacao(locacao);
            
            // Criando segundo mês de aluguel (ainda não pago)
            Aluguel aluguel2 = new Aluguel();
            aluguel2.setDataVencimento(LocalDate.now().plusMonths(2));
            aluguel2.setLocacao(locacao);
            
            aluguelRepo.salvaOuAtualiza(aluguel1);
            aluguelRepo.salvaOuAtualiza(aluguel2);

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
