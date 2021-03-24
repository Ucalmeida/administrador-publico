package org.gestorpublico.entidade;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
public class UF implements Serializable, Comparable<UF> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nm_nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "nm_sigla", length = 2, nullable = false)
    private String sigla;

    // **************************** RELACIONAMENTOS *************************
    @ManyToOne
    @JoinColumn(name="fk_pais", nullable=false, foreignKey=@ForeignKey(name="FK_Pais_UF"))
    private Pais pais;

    // **************************** CONTRUTORES *****************************

    // ****************** HASH, EQUALS, COMPARETO, TOSTRING *****************
    @Override
    public int compareTo(UF o) {
        int compare = this.pais.getId().compareTo(o.getPais().getId());
        return compare != 0 ? compare : nome.compareTo(o.getNome());
    }

    // ****************** GETs e SETs ***************************************
    public void setNome(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? null : nome.trim();
    }

    public void setSigla(String sigla) {
        this.sigla = sigla == null || sigla.trim().isEmpty() ? null : sigla.trim();
    }
    
}
