package org.gestorpublico.tags;

import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import java.util.UUID;
import java.util.logging.Logger;

public class GepTags extends BodyTagSupport {

    JspWriter out;
    Logger logger = Logger.getLogger(getClass().getName());
    String html;
    Session session;
    Pessoa pessoaLogada;
    boolean layoutNovo;

    GepTags() {

    }

    @Override
    public int doStartTag() throws JspException {
        setUp();
//        int retorno = doInitTag();
//        try {
//            out.println(html);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return retorno;
        return 2;
    }

    protected int doInitTag() {
        return 0;
    }

    protected void setUp() {
        this.session = HibernateUtil.getSession();
        this.pessoaLogada = (Pessoa) pageContext.getSession().getAttribute("pessoaLogada");
        if (this.getBodyContent() == null) {
            this.out = pageContext.getOut();
        } else {
            this.out = getBodyContent().getEnclosingWriter();
        }
    }

    @Override
    public void setId(String id) {
        this.id = (id != null && !id.isEmpty()) ? id : UUID.randomUUID().toString().substring(0,5);
    }

    public boolean isLayoutNovo() {
        Tag parent = this.getParent();
        boolean layout = true;
        if (parent == null) {
            layout = false;
        } else {
            while (!(parent instanceof Pagina)) {
                parent = parent.getParent();
                if (parent == null) {
                    layout = false;
                    break;
                }
            }
        }
        return layout;
    }

    public String tipoLayout() {
        Tag parent = this.getParent();
        if (parent == null) return "null";
        while (!(parent instanceof Pagina)) {
            parent = parent.getParent();
        }
        return parent.getClass().getSimpleName();
    }

}