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
public class Aluguel implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private LocalDate dataVencimento;
    private Float valorPago;
    private LocalDate dataPagamento;

    @Column(columnDefinition = "TEXT")
    private String obs;

    @ManyToOne
    @JoinColumn(name = "fk_id_Locacao")
    private Locacao locacao;
}
