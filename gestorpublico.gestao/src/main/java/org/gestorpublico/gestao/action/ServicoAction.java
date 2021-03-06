package org.gestorpublico.gestao.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.BeneficioDAO;
import org.gestorpublico.dao.ServicoDAO;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import java.util.List;

@ParentPackage("default")
public class ServicoAction extends PadraoAction {

    private List<Tuple> servicos;

    @Action(value = "servico",
        results = {
            @Result(name = "ok", location = "servicoFormConcede.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            servicos = new ServicoDAO(session).listeServicosPorAtivo(true);

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Tuple> getServicos() {
        return servicos;
    }
}
