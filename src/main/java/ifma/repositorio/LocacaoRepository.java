package ifma.repositorio;

import java.util.List;

import ifma.modelo.Locacao;
import jakarta.persistence.EntityManager;

public class LocacaoRepository {

    final EntityManager manager;
    final DAOGenerico<Locacao> daoGenerico;

    public LocacaoRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<Locacao>(manager);
    }

    public List<Locacao> listarPorLocacao(Long LocacaoId) {
        return manager.createQuery("SELECT l FROM Locacao l WHERE l.Locacao.id = :LocacaoId", Locacao.class)
                .setParameter("LocacaoId", LocacaoId)
                .getResultList();
    }

    public Locacao buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Locacao.class, id);

    }

    public Locacao salvaOuAtualiza(Locacao locacao) {
        return daoGenerico.salvaOuAtualiza(locacao);
    }

    public void remover(Locacao locacao)  {
        daoGenerico.remove(locacao );
    }

}
