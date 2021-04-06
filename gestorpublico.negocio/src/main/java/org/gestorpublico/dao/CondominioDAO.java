package org.gestorpublico.dao;

import org.gestorpublico.entidade.Bairro;
import org.gestorpublico.entidade.Condominio;
import org.gestorpublico.entidade.Municipio;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CondominioDAO extends DAO<Condominio> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Condominio> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Condominio> root;
	private Root<Condominio> rootTuple;

	public CondominioDAO() {}
	
	public CondominioDAO(Session session) {
		super(session, Condominio.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Condominio.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Condominio.class);
		rootTuple = query2.from(Condominio.class);
	}

//	public List<Tuple> listeCondominioPorNomeMunicipio(String nomeMunicipio) {
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
			return new ArrayList<>();
		}
	}
}
