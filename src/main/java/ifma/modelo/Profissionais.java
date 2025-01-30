package ifma.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profissionais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String profissao;
    @Column(nullable = false)
    private String telefone1;
    
    private String telefone2;
    private Float valorPorHora;

    @Column (columnDefinition = "TEXT")
    private String obs;

}
