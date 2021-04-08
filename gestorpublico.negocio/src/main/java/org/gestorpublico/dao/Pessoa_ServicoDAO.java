package org.gestorpublico.dao;

import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.entidade.Servico;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pessoa_ServicoDAO extends DAO<Pessoa_Servico> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Pessoa_Servico> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Pessoa_Servico> root;
	private Root<Pessoa_Servico> rootTuple;

	public Pessoa_ServicoDAO() {}

	public Pessoa_ServicoDAO(Session session) {
		super(session, Pessoa_Servico.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Pessoa_Servico.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Pessoa_Servico.class);
		rootTuple = query2.from(Pessoa_Servico.class);
	}

	public Pessoa_Servico getServico(Pessoa_Servico pessoaServico) {
		return (Pessoa_Servico) localizar(pessoaServico.getId());
	}

	public boolean jaExistePorServicoSolicitanteDataSemDespacho(Servico servico, Pessoa solicitante, LocalDate data) {
		return getServicoPorServicoSolicitanteDataSemDespacho(servico, solicitante, data) != null;
	}

	public boolean jaExistePorServicoSolicitanteSemDespacho(Servico servico, Pessoa solicitante) {
		return getServicoPorServicoSolicitanteSemDespacho(servico, solicitante) != null;
	}

	public Pessoa_Servico getServicoPorServicoSolicitanteSemDespacho(Servico servico, Pessoa solicitante) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("servico"), servico));
			predicates.add(builder.equal(root.get("solicitante"), solicitante));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Pessoa_Servico getServicoPorServicoSolicitanteDataSemDespacho(Servico servico, Pessoa solicitante, LocalDate data) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("servico"), servico));
			predicates.add(builder.equal(root.get("solicitante"), solicitante));
			predicates.add(builder.between(root.get("dataHoraCadastro"), CassUtil.getDataComHoraZerada(data), CassUtil.getDataHoraUltimoMinutoDoDia(data)));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Servico> listarAtivosPorSolicitanteAutorizacao(Pessoa solicitante, Boolean autorizacao) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("solicitante"), solicitante));
			if (autorizacao == null) {
				predicates.add(builder.isNull(root.get("autorizado")));
			} else {
				predicates.add(builder.equal(root.get("autorizado"), autorizacao));
			}

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Servico> listarPorSolicitanteAutorizacao(Pessoa solicitante, int quantidade) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("solicitante"), solicitante));

			return getSession().createQuery(
					query.select(root)
							.where(predicates.toArray(new Predicate[0])))
					.setMaxResults(quantidade)
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Pessoa_Servico> listarPorSolicitantePeriodo(Pessoa solicitante, LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("solicitante"), solicitante));
			predicates.add(builder.between(root.get("dataHoraCadastro"), CassUtil.getDataComHoraZerada(inicio), CassUtil.getDataHoraUltimoMinutoDoDia(termino)));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Servico> listarPorServicoPeriodo(Servico servico, LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("servico"), servico));
			predicates.add(builder.between(root.get("dataHoraCadastro"), CassUtil.getDataComHoraZerada(inicio), CassUtil.getDataHoraUltimoMinutoDoDia(termino)));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Servico> listarAguardandoDespacho() {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.isNull(root.get("autorizado")));

			return getSession().createQuery(
					query.select(root)
							.where(predicates.toArray(new Predicate[0]))
							.distinct(true))
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Tuple> listeAguardandoDespacho() {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.isNull(rootTuple.get("autorizado")));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(builder.function("date_format", Long.class, rootTuple.get("dataHoraCadastro"), builder.literal("%d/%m/%Y %H:%i")).alias("data"));
			columns.add(rootTuple.join("solicitante").get("nome").alias("solicitante"));
			columns.add(rootTuple.join("servico").get("nome").alias("servico"));
			columns.add(rootTuple.get("observacao").alias("observacao"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(predicates.toArray(new Predicate[0]))
							.distinct(true))
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
