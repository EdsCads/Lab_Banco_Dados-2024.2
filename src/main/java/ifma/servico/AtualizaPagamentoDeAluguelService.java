package ifma.servico;

import java.time.LocalDate;

import ifma.modelo.Aluguel;
import ifma.repositorio.AluguelRepositorio;
import jakarta.persistence.EntityManager;

public class AtualizaPagamentoDeAluguelService {
    
    private final AluguelRepositorio aluguelRepositorio;
    private final EntityManager manager;

    public AtualizaPagamentoDeAluguelService(EntityManager manager) {
        this.manager = manager;
        this.aluguelRepositorio = new AluguelRepositorio(manager);
    }

    public void registraPagamento(Integer idAluguel, Float valorPago) {
        try {
            manager.getTransaction().begin();
            
            Aluguel aluguel = aluguelRepositorio.buscaPorId(idAluguel);
            if (aluguel == null) {
                throw new IllegalArgumentException("Aluguel não encontrado");
            }

            // Verifica se o aluguel já foi pago
            if (aluguel.getValorPago() != null) {
                throw new IllegalStateException("Este aluguel já foi pago");
            }

            // Verifica se o valor pago é igual ao valor do aluguel
            if (!valorPago.equals(aluguel.getLocacao().getValorAluguel())) {
                throw new IllegalArgumentException("Valor pago diferente do valor do aluguel");
            }

            // Atualiza o aluguel com as informações de pagamento
            aluguel.setValorPago(valorPago);
            aluguel.setDataPagamento(LocalDate.now());

            // Calcula multa se necessário
            if (LocalDate.now().isAfter(aluguel.getDataVencimento())) {
                Float percentualMulta = aluguel.getLocacao().getPercentualMulta();
                if (percentualMulta != null && percentualMulta > 0) {
                    Float valorMulta = valorPago * percentualMulta;
                    aluguel.setObs("Pagamento com multa de R$ " + valorMulta);
                }
            }

            aluguelRepositorio.salvaOuAtualiza(aluguel);
            manager.getTransaction().commit();

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao registrar pagamento: " + e.getMessage(), e);
        }
    }

    public void estornarPagamento(Integer idAluguel) {
        try {
            manager.getTransaction().begin();
            
            Aluguel aluguel = aluguelRepositorio.buscaPorId(idAluguel);
            if (aluguel == null) {
                throw new IllegalArgumentException("Aluguel não encontrado");
            }

            // Verifica se o aluguel está pago
            if (aluguel.getValorPago() == null) {
                throw new IllegalStateException("Este aluguel não está pago");
            }

            // Limpa as informações de pagamento
            aluguel.setValorPago(null);
            aluguel.setDataPagamento(null);
            aluguel.setObs(aluguel.getObs() + " | Pagamento estornado em " + LocalDate.now());

            aluguelRepositorio.salvaOuAtualiza(aluguel);
            manager.getTransaction().commit();

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao estornar pagamento: " + e.getMessage(), e);
        }
    }
}
