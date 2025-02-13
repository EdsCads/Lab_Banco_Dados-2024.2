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

    public Aluguel buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Aluguel.class, id);
    }

    public Aluguel salvaOuAtualiza(Aluguel aluguel) {
        return daoGenerico.salvaOuAtualiza(aluguel);
    }

    public void remover(Aluguel aluguel)  {
       
    }
}
