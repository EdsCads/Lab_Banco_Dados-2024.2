package ifma.repositorio;

import ifma.modelo.Imovel;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImovelRepositorio {

    final EntityManager manager;
    final DAOGenerico<Imovel> daoGenerico;

    public ImovelRepositorio(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<Imovel>(manager);
    }
    

    public List<Imovel> todosImoveis() {
        return this.manager.
                createQuery("SELECT i FROM Imovel i", Imovel.class)
                .getResultList();
    }

    public Imovel buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Imovel.class, id);

    }

    public Imovel salvaOuAtualiza(Imovel imovel) {
        return daoGenerico.salvaOuAtualiza(imovel);
    }

    public void remover(Imovel imovel)  {
        daoGenerico.remove(imovel );
    }

}