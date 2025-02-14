package ifma.test;

import java.time.LocalDate;
import java.util.List;

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

public class TesteBuscaAlugueisPagosAtrasados {
    
    public static void main(String[] args) {
        EMFactory factory = new EMFactory();
        EntityManager em = factory.getEntityManager();
        
        ClienteRepositorio clienteRepo = new ClienteRepositorio(em);
        ImovelRepositorio imovelRepo = new ImovelRepositorio(em);
        LocacaoRepository locacaoRepo = new LocacaoRepository(em);
        AluguelRepositorio aluguelRepo = new AluguelRepositorio(em);

        try {
            em.getTransaction().begin();

            // Criando dados de teste
            Cliente proprietario = new Cliente();
            proprietario.setNome("José Silva");
            proprietario.setCpf("12345678901");
            proprietario.setEmail("jose@email.com");
            clienteRepo.salvaOuAtualiza(proprietario);

            Cliente inquilino = new Cliente();
            inquilino.setNome("Ana Santos");
            inquilino.setCpf("98765432101");
            inquilino.setEmail("ana@email.com");
            clienteRepo.salvaOuAtualiza(inquilino);

            Imovel imovel = new Imovel();
            imovel.setLogradouro("Rua Principal, 100");
            imovel.setBairro("Centro");
            imovel.setCep("65000-000");
            imovel.setMetragem(70);
            imovel.setTipoImovel(TipoImovel.APARTAMENTO);
            imovel.setProprietario(proprietario);
            imovelRepo.salvaOuAtualiza(imovel);

            Locacao locacao = new Locacao();
            locacao.setValorAluguel(1200.0f);
            locacao.setDiaVencimento(5);
            locacao.setAtivo(true);
            locacao.setDataInicio(LocalDate.now().minusMonths(3));
            locacao.setPercentualMulta(0.1f);
            locacao.setImovel(imovel);
            locacao.setInquilino(inquilino);
            locacaoRepo.salvaOuAtualiza(locacao);

            // Criando aluguéis com diferentes situações de pagamento
            
            // Aluguel 1 - Pago em dia
            Aluguel aluguelEmDia = new Aluguel();
            aluguelEmDia.setDataVencimento(LocalDate.now().minusMonths(2));
            aluguelEmDia.setDataPagamento(LocalDate.now().minusMonths(2));
            aluguelEmDia.setValorPago(1200.0f);
            aluguelEmDia.setLocacao(locacao);
            aluguelRepo.salvaOuAtualiza(aluguelEmDia);

            // Aluguel 2 - Pago com atraso
            Aluguel aluguelAtrasado1 = new Aluguel();
            aluguelAtrasado1.setDataVencimento(LocalDate.now().minusMonths(1));
            aluguelAtrasado1.setDataPagamento(LocalDate.now().minusMonths(1).plusDays(10));
            aluguelAtrasado1.setValorPago(1320.0f); // Valor com multa
            aluguelAtrasado1.setLocacao(locacao);
            aluguelRepo.salvaOuAtualiza(aluguelAtrasado1);

            // Aluguel 3 - Pago com muito atraso
            Aluguel aluguelAtrasado2 = new Aluguel();
            aluguelAtrasado2.setDataVencimento(LocalDate.now().minusMonths(1));
            aluguelAtrasado2.setDataPagamento(LocalDate.now().minusMonths(1).plusDays(20));
            aluguelAtrasado2.setValorPago(1320.0f); // Valor com multa
            aluguelAtrasado2.setLocacao(locacao);
            aluguelRepo.salvaOuAtualiza(aluguelAtrasado2);

            em.getTransaction().commit();

            // Buscando e exibindo aluguéis pagos com atraso
            System.out.println("\nAluguéis Pagos com Atraso:");
            System.out.println("----------------------------------------");
            
            List<Aluguel> alugueisAtrasados = aluguelRepo.alugueisComPagamentoAtrasado();
            
            if (alugueisAtrasados.isEmpty()) {
                System.out.println("Nenhum aluguel pago com atraso encontrado.");
            } else {
                for (Aluguel aluguel : alugueisAtrasados) {
                    System.out.println("Inquilino: " + aluguel.getLocacao().getInquilino().getNome());
                    System.out.println("Imóvel: " + aluguel.getLocacao().getImovel().getLogradouro());
                    System.out.println("Data Vencimento: " + aluguel.getDataVencimento());
                    System.out.println("Data Pagamento: " + aluguel.getDataPagamento());
                    System.out.println("Valor Original: R$ " + aluguel.getLocacao().getValorAluguel());
                    System.out.println("Valor Pago: R$ " + aluguel.getValorPago());
                    System.out.println("----------------------------------------");
                }
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
