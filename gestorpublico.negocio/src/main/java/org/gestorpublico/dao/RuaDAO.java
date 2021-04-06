package org.gestorpublico.dao;

import org.gestorpublico.entidade.Bairro;
import org.gestorpublico.entidade.Rua;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RuaDAO extends DAO<Rua> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Rua> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Rua> root;
	private Root<Rua> rootTuple;

	public RuaDAO() {}
	
	public RuaDAO(Session session) {
		super(session, Rua.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Rua.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Rua.class);
		rootTuple = query2.from(Rua.class);
	}

//	public List<Tuple> listeRuaPorNomeMunicipio(String nomeMunicipio) {
//		try {
//			List<Predicate> predicates = new ArrayList<Predicate>();
//			predicates.add(builder.equal(rootTuple.join("municipio").get("nome"), nomeMunicipio));
//
//			List<Selection<?>> columns = new ArrayList<Selection<?>>();
//			columns.add(rootTuple.<Integer>get("id").alias("id"));
//			columns.add(rootTuple.<String>get("nome").alias("nome"));
//
//			return getSession().createQuery(
//					query2.multiselect(columns)
//							.where(predicates.toArray(new Predicate[0]))
//							.orderBy(builder.asc(rootTuple.get("nome"))))
//					.getResultList();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<>();
//		}
//	}

	public List<Tuple> listePorBairro(Bairro bairro) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(rootTuple.get("bairro"), bairro));

			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.where(predicates.toArray(new Predicate[0]))
							.orderBy(builder.asc(rootTuple.get("nome"))))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Tuple>();
		}
	}
}
