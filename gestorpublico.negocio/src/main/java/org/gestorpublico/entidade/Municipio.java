package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(
    indexes = {
        @Index(columnList = "nm_nome", name = "nome"),
        @Index(columnList = "nm_sigla", name = "sigla")
    }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
public class Municipio implements Serializable, Comparable<Municipio> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "nm_codigoIBGE", length = 8, nullable = false, columnDefinition = "varchar(9) default 0")
    private String codigoIBGE;

    @Column(name = "in_populacao", nullable = false, columnDefinition = "int default 0")
    private String populacao;

    @Column(name = "dt_funcacao", columnDefinition = "date")
    private LocalDate dataFuncacao;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_uf", nullable=false, foreignKey=@ForeignKey(name="FK_UF_Municipio"))
    private UF uf;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(Municipio o) {
        int compare = this.uf.compareTo(o.getUf());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

    public void setSigla(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE == null || codigoIBGE.trim().isEmpty() ? null : codigoIBGE.trim();
    }
    
}
