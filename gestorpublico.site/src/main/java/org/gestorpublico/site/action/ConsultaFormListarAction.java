package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import java.util.List;

@ParentPackage("default")
public class ConsultaFormListarAction extends PadraoAction {

//    private List<Pessoa_Consulta> consultas;

    @Action(value = "consultas",
        results = {
            @Result(name = "ok", location = "consultaFormLista.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
//            servicos = new Pessoa_ServicoDAO(session).listarAtivosPorSolicitanteAutorizacao(getPessoaLogada(), null);

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
}
