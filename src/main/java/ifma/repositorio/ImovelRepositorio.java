package ifma.repositorio;

import ifma.modelo.Imovel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class ImovelRepositorio extends DAOGenerico<Imovel, Integer> {

    public ImovelRepositorio(EntityManager manager) {
        super(manager);
    }

    @Override
    public void salvar(Imovel imovel) {
        if (imovel.getProprietario() == null) {
            throw new IllegalArgumentException("O imóvel deve ter um proprietário");
        }
        super.salvar(imovel);
    }

    @Override
    public void atualizar(Imovel imovel) {
        if (imovel.getProprietario() == null) {
            throw new IllegalArgumentException("O imóvel deve ter um proprietário");
        }
        super.atualizar(imovel);
    }

    public List<Imovel> buscarPorBairro(String bairro) {
        return buscarPorCampo("bairro", bairro);
    }

    public List<Imovel> buscarPorFaixaDeValor(Float valorMinimo, Float valorMaximo) {
        String jpql = "SELECT i FROM Imovel i WHERE i.valorAluguelSugerido BETWEEN ?1 AND ?2";
        return criarQuery(jpql, valorMinimo, valorMaximo).getResultList();
    }

    public Optional<Imovel> buscarPorCep(String cep) {
        try {
            List<Imovel> resultado = buscarPorCampo("cep", cep);
            return resultado.isEmpty() ? Optional.empty() : Optional.of(resultado.get(0));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Imovel> buscarPorCpfProprietario(String cpf) {
        String jpql = "SELECT i FROM Imovel i WHERE i.proprietario.cpf = ?1";
        return criarQuery(jpql, cpf).getResultList();
    }
}