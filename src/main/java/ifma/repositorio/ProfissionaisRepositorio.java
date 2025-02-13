package ifma.repositorio;

import java.util.List;

import ifma.modelo.Profissionais;
import jakarta.persistence.EntityManager;

public class ProfissionaisRepositorio {
    final EntityManager manager;
    final DAOGenerico<Profissionais> daoGenerico;

    public ProfissionaisRepositorio(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<Profissionais>(manager);
    }


    public List<Profissionais> todosProfissionais() {
        return this.manager.
                createQuery("SELECT i FROM Profissionais i", Profissionais.class)
                .getResultList();
    }

    public Profissionais buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Profissionais.class, id);

    }

    public Profissionais salvaOuAtualiza(Profissionais profissionais) {
        return daoGenerico.salvaOuAtualiza(profissionais);
    }

    public void remover(Profissionais profissionais)  {
        daoGenerico.remove(profissionais );
    }
}
