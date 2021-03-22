package org.gestorpublico.dao;

import org.gestorpublico.entidade.Sexo;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class SexoDAO extends DAO<Sexo> {

    public SexoDAO() {}

    public SexoDAO(Session session) {
        super(session, Sexo.class);
    }

    public boolean jaExisteSexoPorNome(String nome) {
        return getSexoPorNome(nome) != null;
    }

    public Sexo getSexoPorNome(String nome) {
        return (Sexo) getSession().createCriteria(Sexo.class)
                .add(Restrictions.eq("nome", nome))
                .uniqueResult();
    }

    public Sexo getSexo(Sexo sexo) {
        return (Sexo) localizar(sexo.getId());
    }

    public List<Sexo> listarSexos() {
        return getSession().createCriteria(Sexo.class)
                .list();
    }
}
