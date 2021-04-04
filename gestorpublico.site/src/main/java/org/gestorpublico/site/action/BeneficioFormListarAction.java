package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Pessoa_BeneficioDAO;
import org.gestorpublico.entidade.Pessoa_Beneficio;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import java.util.List;

@ParentPackage("default")
public class BeneficioFormListarAction extends PadraoAction {

    private List<Pessoa_Beneficio> beneficios;

    @Action(value = "beneficios",
        results = {
            @Result(name = "ok", location = "beneficioFormLista.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            beneficios = new Pessoa_BeneficioDAO(session).listarAtivosPorSolicitanteOuBeneficiadoAutorizacao(getPessoaLogada(), null);

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Pessoa_Beneficio> getBeneficios() {
        return beneficios;
    }
}
