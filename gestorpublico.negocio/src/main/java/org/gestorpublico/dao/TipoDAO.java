package org.gestorpublico.dao;

import org.gestorpublico.entidade.Objeto;
import org.gestorpublico.entidade.Poder_Setor;
import org.gestorpublico.entidade.Tipo;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO extends DAO<Tipo> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Tipo> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Tipo> root;
	private Root<Tipo> rootTuple;

	public TipoDAO() {}
	
	public TipoDAO(Session session) {
		super(session, Tipo.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Tipo.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Tipo.class);
		rootTuple = query2.from(Tipo.class);
	}

	public boolean jaExistePorObjetoNomeTipo(Objeto objeto, String nomeTipo) {
		return getTipoPorObjetoNomeTipo(objeto, nomeTipo) != null;
	}

	public Tipo getTipo(Tipo Tipo) {
		return (Tipo) localizar(Tipo.getId());
	}

	public Tipo getTipoPorObjetoNomeTipo(String nomeObjeto, String nomeTipo) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.join("objeto").get("nome"), nomeObjeto));
			predicates.add(builder.equal(root.get("nome"), nomeTipo));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	public Tipo getTipoPorObjetoNomeTipo(Objeto objeto, String nomeTipo) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("objeto"),objeto));
			predicates.add(builder.equal(root.get("nome"), nomeTipo));

			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
