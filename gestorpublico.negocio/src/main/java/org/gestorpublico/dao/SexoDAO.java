package org.gestorpublico.dao;

import org.gestorpublico.entidade.Sexo;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SexoDAO extends DAO<Sexo> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Sexo> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Sexo> root;
	private Root<Sexo> rootTuple;

	public SexoDAO() {}

	public SexoDAO(Session session) {
		super(session, Sexo.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Sexo.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Sexo.class);
		rootTuple = query2.from(Sexo.class);
	}

	public Sexo getSexoPorNome(String nome) {
		try {
			return getSession().createQuery(
					query.select(root)
							.where(builder.equal(root.get("nome"), nome))
							.orderBy(builder.asc(root.get("nome"))))
					.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Tuple> listeSexos() {
		try {
			List<Selection<?>> columns = new ArrayList<Selection<?>>();
			columns.add(rootTuple.<Integer>get("id").alias("id"));
			columns.add(rootTuple.<String>get("nome").alias("nome"));

			return getSession().createQuery(
					query2.multiselect(columns)
							.orderBy(builder.asc(rootTuple.get("nome"))))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<Sexo> listarSexos() {
		try {
			return getSession().createQuery(
					query.select(root)
							.orderBy(builder.asc(root.get("nome"))))
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
