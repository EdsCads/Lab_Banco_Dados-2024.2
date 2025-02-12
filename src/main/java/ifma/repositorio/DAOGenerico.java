package ifma.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class DAOGenerico<T, ID> {
    
    protected final EntityManager manager;
    private final Class<T> classe;

    @SuppressWarnings("unchecked")
    public DAOGenerico(EntityManager manager) {
        this.manager = manager;
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.classe = (Class<T>) type.getActualTypeArguments()[0];
    }

    public void salvar(T entidade) {
        manager.persist(entidade);
    }

    public void atualizar(T entidade) {
        manager.merge(entidade);
    }

    public void remover(T entidade) {
        manager.remove(manager.contains(entidade) ? entidade : manager.merge(entidade));
    }

    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(manager.find(classe, id));
    }

    public List<T> listarTodos() {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(classe);
        Root<T> root = query.from(classe);
        query.select(root);
        
        TypedQuery<T> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<T> buscarPorCampo(String nomeCampo, Object valor) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(classe);
        Root<T> root = query.from(classe);
        
        query.where(builder.equal(root.get(nomeCampo), valor));
        
        return manager.createQuery(query).getResultList();
    }

    protected TypedQuery<T> criarQuery(String jpql, Object... params) {
        TypedQuery<T> query = manager.createQuery(jpql, classe);
        
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        
        return query;
    }

    public void iniciarTransacao() {
        if (!manager.getTransaction().isActive()) {
            manager.getTransaction().begin();
        }
    }

    public void commitarTransacao() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
        }
    }

    public void rollbackTransacao() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
        }
    }
}
