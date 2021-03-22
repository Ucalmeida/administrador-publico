package org.gestorpublico.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.gestorpublico.util.CassUtil;
import org.hibernate.annotations.Type;

@Entity
@Table(
	indexes={
		@Index(columnList="dh_cadastro", name="dh_cadastro"),
		@Index(columnList="action", name="action")
	}
)
public class Log_Erro_Execucao implements Serializable, Comparable<Log_Erro_Execucao> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="dh_cadastro", nullable=false, columnDefinition="datetime")
	private LocalDateTime dataHoraCadastro;

	@Column(name="action", length=100, nullable=false)
	private String action;

	@Type(type="text")
	@Column(name="nm_erro", nullable=false)
	private String erro;

	// *********************************** RELACIONAMENTOS ************************************

	// ************************************* CONSTRUTORES *************************************
	public Log_Erro_Execucao() {
		this.setDataHoraCadastro(LocalDateTime.now());
	}

	public Log_Erro_Execucao(String action, String erro) {
		this.setDataHoraCadastro(LocalDateTime.now());
		this.setAction(action);
		this.setErro(erro);
	}

	// ******************************* HASH, EQUALS e COMPARETO *******************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log_Erro_Execucao other = (Log_Erro_Execucao) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int compareTo(Log_Erro_Execucao o) {
		if (this.id < o.getId()) {
            return -1;
        }
        if (this.id > o.getId()) {
            return 1;
        }
		return 0;
	}

	// ************************************* GETS e SETs **************************************
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public String getDataHoraCadastroFormatada() {
		return CassUtil.getDataHoraFormatada(dataHoraCadastro);
	}

	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public void setDataHoraCadastro(String dataHoraCadastro) {
		this.dataHoraCadastro = CassUtil.converterDataHoraStringParaLocalDateTime(dataHoraCadastro);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}
