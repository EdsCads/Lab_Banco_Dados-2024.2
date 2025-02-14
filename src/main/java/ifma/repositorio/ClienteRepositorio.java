package ifma.repositorio;

import ifma.modelo.Cliente;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ClienteRepositorio {

    final EntityManager manager;
    final DAOGenerico<Cliente> daoGenerico;

    public ClienteRepositorio(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<Cliente>(manager);
    }
    

    public List<Cliente> todosClientes() {
        return this.manager.
                createQuery("SELECT i FROM Cliente i", Cliente.class)
                .getResultList();
    }

    public Cliente buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Cliente.class, id);

    }

    public Cliente salvaOuAtualiza(Cliente cliente) {
        return daoGenerico.salvaOuAtualiza(cliente);
    }

    public void remover(Cliente cliente)  {
        daoGenerico.remove(cliente );
    }

}