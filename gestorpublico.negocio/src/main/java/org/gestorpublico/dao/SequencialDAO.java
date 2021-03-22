package org.gestorpublico.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.gestorpublico.entidade.Sequencial;
import org.hibernate.Session;

public class SequencialDAO extends DAO<Sequencial> {
	
	private CriteriaBuilder builder;
	private CriteriaQuery<Sequencial> query;
	private CriteriaQuery<Tuple> query2;
	private Root<Sequencial> root;
	
	public SequencialDAO() {}
	
	public SequencialDAO(Session session) {
		super(session, Sequencial.class);
		builder = session.getCriteriaBuilder();
		query = builder.createQuery(Sequencial.class);
		query2 = builder.createQuery(Tuple.class);
		root = query.from(Sequencial.class);
	}
	
	public Sequencial getSequencial(Integer x) {
		return (Sequencial) localizar(x);
	}
	
	public Sequencial getSequencialPorNome(String nome) {
		try {
			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(builder.equal(root.get("nome"), nome));
			
			return getSession().createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
					.getSingleResult();
					
		} catch (Exception e) {
			return null;
		}
	}
		
}