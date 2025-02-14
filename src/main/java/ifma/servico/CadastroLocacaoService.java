package ifma.servico;

import java.time.LocalDate;
import java.util.List;

import ifma.modelo.Imovel;
import ifma.modelo.Locacao;
import ifma.repositorio.ImovelRepositorio;
import ifma.repositorio.LocacaoRepository;
import jakarta.persistence.EntityManager;

public class CadastroLocacaoService {
    
    private final ImovelRepositorio imovelRepositorio;
    private final LocacaoRepository locacaoRepository;
    private final EntityManager manager;

    public CadastroLocacaoService(EntityManager manager) {
        this.manager = manager;
        this.imovelRepositorio = new ImovelRepositorio(manager);
        this.locacaoRepository = new LocacaoRepository(manager);
    }

    public void registrarLocacao(Locacao novaLocacao) {
        try {
            manager.getTransaction().begin();
            
            // Verifica se o imóvel existe
            Imovel imovel = imovelRepositorio.buscaPorId(novaLocacao.getImovel().getId());
            if (imovel == null) {
                throw new IllegalArgumentException("Imóvel não encontrado");
            }

            // Verifica se o imóvel está disponível para locação
            if (!imovel.getDisponivel()) {
                throw new IllegalStateException("Imóvel não está disponível para locação");
            }

            // Verifica se existe alguma locação ativa para este imóvel
            List<Locacao> locacoesAtivas = locacaoRepository.buscarLocacoesAtivasPorImovel(imovel.getId());
            if (!locacoesAtivas.isEmpty()) {
                throw new IllegalStateException("Imóvel já possui uma locação ativa");
            }

            // Verifica se a data de início é válida
            if (novaLocacao.getDataInicio().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Data de início não pode ser anterior à data atual");
            }

            // Verifica se o valor do aluguel é válido
            if (novaLocacao.getValorAluguel() <= 0) {
                throw new IllegalArgumentException("Valor do aluguel deve ser maior que zero");
            }

            // Marca o imóvel como não disponível
            imovel.setDisponivel(false);
            imovelRepositorio.salvaOuAtualiza(imovel);

            // Salva a nova locação
            locacaoRepository.salvaOuAtualiza(novaLocacao);
            
            manager.getTransaction().commit();

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e; // Relança a exceção para ser tratada pela camada superior
        }
    }

    public void finalizarLocacao(Integer idLocacao) {
        try {
            manager.getTransaction().begin();

            Locacao locacao = locacaoRepository.buscaPorId(idLocacao);
            if (locacao == null) {
                throw new IllegalArgumentException("Locação não encontrada");
            }

            // Verifica se a locação já está inativa
            if (!locacao.getAtivo()) {
                throw new IllegalStateException("Locação já está finalizada");
            }

            // Finaliza a locação
            locacao.setAtivo(false);
            locacao.setDataFim(LocalDate.now());
            
            // Marca o imóvel como disponível novamente
            Imovel imovel = locacao.getImovel();
            imovel.setDisponivel(true);
            
            imovelRepositorio.salvaOuAtualiza(imovel);
            locacaoRepository.salvaOuAtualiza(locacao);

            manager.getTransaction().commit();

        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            throw e;
        }
    }
}
