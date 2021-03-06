package org.gestorpublico.dao;

import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pessoa_BeneficioDAO extends DAO<Pessoa_Beneficio> {

	private CriteriaBuilder builder;
	private CriteriaQuery<Pessoa_Beneficio> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Pessoa_Beneficio> root;
	private Root<Pessoa_Beneficio> rootTuple;

	public Pessoa_BeneficioDAO() {}

	public Pessoa_BeneficioDAO(Session session) {
		super(session, Pessoa_Beneficio.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Pessoa_Beneficio.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Pessoa_Beneficio.class);
		rootTuple = query2.from(Pessoa_Beneficio.class);
	}

	public Pessoa_Beneficio getBeneficio(Pessoa_Beneficio pessoaBeneficio) {
		return (Pessoa_Beneficio) localizar(pessoaBeneficio.getId());
	}

	public boolean jaExistePorBeneficioSolicitanteDataSemDespacho(Beneficio beneficio, Pessoa solicitante, LocalDate data) {
		return getBeneficioPorBeneficioSolicitanteDataSemDespacho(beneficio, solicitante, data) != null;
	}

	public boolean jaExistePorBeneficioBeneficiadoSemDespacho(Beneficio beneficio, Pessoa beneficiado) {
		return getBeneficioPorBeneficioBeneficiadoSemDespacho(beneficio, beneficiado) != null;
	}

	public Pessoa_Beneficio getBeneficioPorBeneficioBeneficiadoSemDespacho(Beneficio beneficio, Pessoa beneficiado) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("beneficio"), beneficio));
			predicates.add(builder.equal(root.get("beneficiado"), beneficiado));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Pessoa_Beneficio getBeneficioPorBeneficioSolicitanteDataSemDespacho(Beneficio beneficio, Pessoa solicitante, LocalDate data) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("beneficio"), beneficio));
			predicates.add(builder.equal(root.get("solicitante"), solicitante));
			predicates.add(builder.between(root.get("dataHoraCadastro"), CassUtil.getDataComHoraZerada(data), CassUtil.getDataHoraUltimoMinutoDoDia(data)));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Beneficio> listarAguardandoDespacho() {
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
			columns.add(rootTuple.join("beneficiado").get("nome").alias("beneficiado"));
			columns.add(rootTuple.join("beneficio").get("nome").alias("beneficio"));
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

	public List<Pessoa_Beneficio> listarPorSolicitanteOuBeneficiadoAutorizacao(Pessoa solicitante, int quantidade) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.or(
					builder.equal(root.get("solicitante"), solicitante),
					builder.equal(root.get("beneficiado"), solicitante)));

			return getSession().createQuery(
					query.select(root)
							.where(predicates.toArray(new Predicate[0]))
							.distinct(true))
					.setMaxResults(quantidade)
					.getResultList();

		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Pessoa_Beneficio> listarAtivosPorSolicitanteOuBeneficiadoAutorizacao(Pessoa solicitante, Boolean autorizacao) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.or(
					builder.equal(root.get("solicitante"), solicitante),
					builder.equal(root.get("beneficiado"), solicitante)));
			if (autorizacao == null) {
				predicates.add(builder.isNull(root.get("autorizado")));
			} else {
				predicates.add(builder.equal(root.get("autorizado"), autorizacao));
			}

			return getSession().createQuery(
					query.select(root)
							.where(predicates.toArray(new Predicate[0]))
							.distinct(true))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

	public List<Pessoa_Beneficio> listarPorSolicitantePeriodo(Pessoa solicitante, LocalDate inicio, LocalDate termino) {
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

	public List<Pessoa_Beneficio> listarPorBeneficioPeriodo(Beneficio beneficio, LocalDate inicio, LocalDate termino) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("beneficio"), beneficio));
			predicates.add(builder.between(root.get("dataHoraCadastro"), CassUtil.getDataComHoraZerada(inicio), CassUtil.getDataHoraUltimoMinutoDoDia(termino)));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();

		} catch (Exception e) {
			return null;
		}
	}

    public List<Tuple> listePorPessoa(Pessoa pessoa) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("beneficiado"), pessoa));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(builder.function("date_format", Long.class, rootTuple.get("dataHoraCadastro"), builder.literal("%d/%m/%Y %H:%i")).alias("data"));
			columns.add(rootTuple.join("beneficio").get("nome").alias("beneficio"));
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
