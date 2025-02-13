package ifma.modelo;

import java.time.LocalDate;

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
public class ServicosImovel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDate dataServico;
    private Float valorTotal;

    @Column (columnDefinition = "TEXT")
    private String obs;

    @ManyToOne
    @JoinColumn(name = "fk_id_Profissionais")
    private Profissionais profissionais;

    @ManyToOne
    @JoinColumn(name = "fk_id_imovel")
    private Imovel imovel;

}
