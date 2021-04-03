package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.dao.Pessoa_DependenteDAO;
import org.gestorpublico.entidade.Pessoa_Dependente;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import java.util.List;

@ParentPackage("default")
public class DependenteListarAction extends PadraoAction {

    private List<Pessoa_Dependente> dependentes;

    @Action(value = "dependentes",
        results = {
            @Result(name = "ok", location = "dependenteLista.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            dependentes = getPessoaLogada().getDependentes();

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Pessoa_Dependente> getDependentes() {
        return dependentes;
    }
}
