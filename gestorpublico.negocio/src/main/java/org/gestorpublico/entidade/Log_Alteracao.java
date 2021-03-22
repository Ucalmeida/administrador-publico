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
		@Index(columnList="fk_executor", name="fk_executor"),
		@Index(columnList="action", name="action"),
		@Index(columnList="fk_idAlterado", name="fk_idAlterado")
	}
)
public class Log_Alteracao implements Serializable, Comparable<Log_Alteracao> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="dh_cadastro", nullable=false, columnDefinition="datetime")
	private LocalDateTime dataHoraCadastro;

	@Column(name="fk_executor", nullable=false)
	private Integer executor;

	@Column(name="action", length=100, nullable=false)
	private String action;

	@Column(name="fk_idAlterado")
	private Integer idAlterado;

	@Type(type="text")
	@Column(name="nm_descricao", nullable=false)
	private String descricao;

	// *********************************** RELACIONAMENTOS ************************************

	// ************************************* CONSTRUTORES *************************************
	public Log_Alteracao() {
		this.setDataHoraCadastro(CassUtil.getDataHoraAtualPorFormato("dd/MM/yyyy HH:mm:ss"));
	}

	public Log_Alteracao(Integer executor, String action, String descricao) {
		this.setDataHoraCadastro(CassUtil.getDataHoraAtualPorFormato("dd/MM/yyyy HH:mm:ss"));
		this.setExecutor(executor);
		this.setAction(action);
		this.setDescricao(descricao);
	}

	public Log_Alteracao(Integer executor, String action, String descricao, Integer idAlterado) {
		this.setDataHoraCadastro(CassUtil.getDataHoraAtualPorFormato("dd/MM/yyyy HH:mm:ss"));
		this.setExecutor(executor);
		this.setAction(action);
		this.setDescricao(descricao);
		this.setIdAlterado(idAlterado);
	}

	public Log_Alteracao(String executor, String action, String descricao) {
		this.setDataHoraCadastro(CassUtil.getDataHoraAtualPorFormato("dd/MM/yyyy HH:mm:ss"));
		this.setExecutor(executor);
		this.setAction(action);
		this.setDescricao(descricao);
	}

	public Log_Alteracao(String executor, String action, String descricao, String idAlterado) {
		this.setDataHoraCadastro(CassUtil.getDataHoraAtualPorFormato("dd/MM/yyyy HH:mm:ss"));
		this.setExecutor(executor);
		this.setAction(action);
		this.setDescricao(descricao);
		this.setIdAlterado(idAlterado);
	}

	// ******************************* HASH, EQUALS e COMPARETO *******************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((executor == null) ? 0 : executor.hashCode());
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
		Log_Alteracao other = (Log_Alteracao) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (executor == null) {
			if (other.executor != null)
				return false;
		} else if (!executor.equals(other.executor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int compareTo(Log_Alteracao o) {
		return id.compareTo(o.getId());
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

	public Integer getExecutor() {
		return executor;
	}

	public void setExecutor(Integer executor) {
		this.executor = executor;
	}

	public void setExecutor(String executor) {
		this.executor = Integer.valueOf(executor);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getIdAlterado() {
		return idAlterado;
	}

	public void setIdAlterado(Integer idAlterado) {
		this.idAlterado = idAlterado;
	}

	public void setIdAlterado(String idAlterado) {
		this.idAlterado = Integer.valueOf(idAlterado);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao == null || descricao.trim().isEmpty() ? null : descricao.trim();
	}

}
