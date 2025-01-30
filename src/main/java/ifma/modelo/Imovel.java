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

@Entity
@Getter
@Setter
public class Imovel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer d;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cep;

    private Integer metragem;
    private Boolean dormitorios;
    private Boolean suites;
    private Boolean vagsGaragem;
    private Float valorAluguelSugerido;

    @Column (columnDefinition = "TEXT")
    private String obs;

    @ManyToOne
    @JoinColumn(name = "fk_id_tipo_imovel")
    private TipoImovel tipoImovel;

    @ManyToOne
    @JoinColumn(name = "fk_id_Cliente")
    private Cliente proprietario;

}
