package org.gestorpublico.hibernate;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;

public class BancoGerar {

	public static void main(String[] args) {
		try {
			Metadata metadata = HibernateUtil.getMetadata();
			SchemaExport schemaExport = new SchemaExport();
			schemaExport.setHaltOnError(true);
			schemaExport.setFormat(true);
			schemaExport.setDelimiter(";");
			schemaExport.setOutputFile("gestorpublico.sql");
			
			// Executa no banco
			schemaExport.execute(EnumSet.of(TargetType.DATABASE), Action.BOTH, metadata);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
