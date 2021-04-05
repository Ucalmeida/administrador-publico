package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.BeneficioDAO;
import org.gestorpublico.dao.Pessoa_DependenteDAO;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import java.util.List;

@ParentPackage("default")
public class BeneficioAction extends PadraoAction {

    private List<Tuple> dependentes;
    private List<Tuple> beneficios;

    @Action(value = "beneficio",
        results = {
            @Result(name = "ok", location = "beneficioFormSolicita.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            dependentes = new Pessoa_DependenteDAO(session).listeDependentesPorAscendente(getPessoaLogada());
            beneficios = new BeneficioDAO(session).listeBeneficiosPorAtivo(true);

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Tuple> getDependentes() {
        return dependentes;
    }

    public List<Tuple> getBeneficios() {
        return beneficios;
    }
}
