package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.util.PortalAction;
import org.hibernate.Session;

@ParentPackage("default")
public class PessoaFormLocalizarAction extends PortalAction {

    @Action(value = "pessoaFormLocalizar",
            results = {
                    @Result(name = "ok", location = "formLocalizarPessoa.jsp"),
                    @Result(name = "erro", type = "httpheader", params = {"status", "409"})
            }
    )
    public String execute() {
        try {
            Session session = getSession();

            return SUCESSO;

        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("erro", e.getMessage());
            return ERRO;
        }
    }

    // ****************************** GETs e SETs ******************************
}