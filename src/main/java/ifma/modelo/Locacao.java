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
public class Locacao implements EntidadeBase{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(nullable = false)
    private Float valorAluguel;
    @Column(nullable = false)
    private Integer diaVencimento;
    @Column(nullable = false)
    private Boolean ativo;
    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;
    private Float percentualMulta;

    @Column (columnDefinition = "TEXT")
    private String obs;
    
    @ManyToOne
    @JoinColumn(name = "fk_id_imovel", nullable = false)
    private Imovel imovel;

    @ManyToOne
    @JoinColumn(name = "fk_id_Cliente", nullable = false)
    private Cliente inquilino;
}
