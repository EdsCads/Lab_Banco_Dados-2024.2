package ifma.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Getter
@Setter
public class Imovel implements EntidadeBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cep;

    private Integer metragem;
    private Boolean temDormitorios;
    private Boolean temSuites;
    private Boolean temVagasGaragem;
    private Float valorAluguelSugerido;

    @Column (columnDefinition = "TEXT")
    private String obs;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_imovel", nullable = false)
    private TipoImovel tipoImovel;

    @ManyToOne
    @JoinColumn(name = "fk_id_Cliente", nullable = false)
    private Cliente proprietario;

    @Column(nullable = false)
    private Boolean disponivel = true; // valor padrão true

    public Integer getId() {
        return id;
    }

}