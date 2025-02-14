package ifma.repositorio;

import java.util.List;

import ifma.modelo.Aluguel;
import jakarta.persistence.EntityManager;

public class AluguelRepositorio {
    final EntityManager manager;
    final DAOGenerico<Aluguel> daoGenerico;

    public AluguelRepositorio(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<Aluguel>(manager);
    }


    public List<Aluguel> todosAluguelDeUmInquilino() {
        return this.manager.
                createQuery("SELECT i FROM Aluguel i", Aluguel.class)
                .getResultList();
    }
    public List<Aluguel> todosAlugueisDoInquilino(Integer idInquilino) {
        return this.manager
                .createQuery("SELECT a FROM Aluguel a WHERE a.inquilino.id = :idInquilino", Aluguel.class)
                .setParameter("idInquilino", idInquilino)
                .getResultList();
    }

    public List<Aluguel> imoveisDisponiveisPorValorMaximo(Double valorMaximo) {
        return this.manager
                .createQuery("SELECT a FROM Aluguel a WHERE a.imovel.disponivel = true AND a.valorAluguelSugerido <= :valorMaximo", Aluguel.class)
                .setParameter("valorMaximo", valorMaximo)
                .getResultList();
    }

    public List<Aluguel> alugueisComPagamentoAtrasado() {
        return this.manager
                .createQuery("SELECT a FROM Aluguel a WHERE a.dataPagamento > a.dataVencimento", Aluguel.class)
                .getResultList();
    }

    public Aluguel buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Aluguel.class, id);
    }

    public Aluguel salvaOuAtualiza(Aluguel aluguel) {
        return daoGenerico.salvaOuAtualiza(aluguel);
    }

    public void remover(Aluguel aluguel)  {
       
    }
}
