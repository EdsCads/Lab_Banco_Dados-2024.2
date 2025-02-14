package ifma.repositorio;

import java.util.List;

import ifma.modelo.ServicosImovel;
import jakarta.persistence.EntityManager;

public class ServicosImovelRepositorio {
    final EntityManager manager;
    final DAOGenerico<ServicosImovel> daoGenerico;

    public ServicosImovelRepositorio(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<ServicosImovel>(manager);
    }


    public List<ServicosImovel> todosServicosImovel() {
        return this.manager.
                createQuery("SELECT i FROM ServicosImovel i", ServicosImovel.class)
                .getResultList();
    }

    public ServicosImovel buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(ServicosImovel.class, id);
    }

    public ServicosImovel salvaOuAtualiza(ServicosImovel servicosImovel) {
        return daoGenerico.salvaOuAtualiza(servicosImovel);
    }

    public void remover(ServicosImovel servicosImovel)  {
       
    }
}