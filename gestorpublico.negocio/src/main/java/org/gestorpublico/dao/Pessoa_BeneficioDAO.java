package org.gestorpublico.dao;

import org.gestorpublico.entidade.Beneficio;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.util.CassUtil;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

	public boolean jaExistePorBeneficioSolicitanteSemDespacho(Beneficio beneficio, Pessoa solicitante) {
		return getBeneficioPorBeneficioSolicitanteSemDespacho(beneficio, solicitante) != null;
	}

	public Pessoa_Beneficio getBeneficioPorBeneficioSolicitanteSemDespacho(Beneficio beneficio, Pessoa solicitante) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("beneficio"), beneficio));
			predicates.add(builder.equal(root.get("solicitante"), solicitante));

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

	public List<Pessoa_Beneficio> listarAtivosPorSolicitanteAutorizacao(Pessoa solicitante, Boolean autorizacao) {
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

}
