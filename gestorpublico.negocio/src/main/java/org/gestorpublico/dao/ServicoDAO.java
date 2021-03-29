package org.gestorpublico.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;

import org.gestorpublico.entidade.Poder_Setor;
import org.gestorpublico.entidade.Servico;
import org.hibernate.Session;

public class ServicoDAO extends DAO<Servico> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Servico> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Servico> root;
	private Root<Servico> rootTuple;

	public ServicoDAO() {}
	
	public ServicoDAO(Session session) {
		super(session, Servico.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Servico.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Servico.class);
		rootTuple = query2.from(Servico.class);
	}

	public boolean jaExistePorPoderSetorNomeServico(Poder_Setor poderSetor, String nomeServico) {
		return getServicoPorPoderSetorNomeServico(poderSetor, nomeServico) != null;
	}

	public Servico getServico(Servico Servico) {
		return (Servico) localizar(Servico.getId());
	}

	public Servico getServicoPorPoderSetorNomeServico(Poder_Setor poderSetor, String nomeServico) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("poderSetor"),poderSetor));
			predicates.add(builder.equal(root.get("nome"), nomeServico));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Servico> listarAtivas() {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("ativo"), true));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

	public List<Servico> listar() {
		try {
			return getSession().createQuery(query.select(root))
					.getResultList();
			
		} catch (Exception e) {
			return null;
		}
	}

	public List<Tuple> listeServicosPorAtivo(boolean ativo) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("ativo"), ativo));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));

			return getSession().createQuery(query2.multiselect(columns).where(predicates.toArray(new Predicate[0])).orderBy(builder.asc(rootTuple.get("nome"))))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
