package org.gestorpublico.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DAO<Obj> {
	
	private Class classe;
	private Session session;
	
	protected Session getSession() {
		return session;
	}
	
	public DAO() {}
	
	public DAO(Session session, Class classe) {
		this.session = session;
		this.classe = classe;
	}
	
	public void salvar(Obj objeto) {
		Transaction ts = session.beginTransaction();
		this.session.save(objeto);
		ts.commit();
	}
	
	public void excluir(Obj objeto) {
		Transaction ts = session.beginTransaction();
		this.session.delete(objeto);
		ts.commit();
	}
	
	public void atualizar(Obj objeto) {
		Transaction ts = session.beginTransaction();
		this.session.update(objeto);
		ts.commit();
	}
	
	public void mescle(Obj objeto) {
		Transaction ts = session.beginTransaction();
		this.session.merge(objeto);
		ts.commit();
	}
	
	public Obj localizar(Serializable obj) {
		Obj o = (Obj) this.session.get(classe, obj); 
		return o; 
	}
	
}