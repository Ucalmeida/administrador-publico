package org.gestorpublico.hibernate;

import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;

public class BancoUpdate {

	public static void main(String[] args) {
		try {
			Metadata metadata = HibernateUtil.getMetadata();
			SchemaUpdate schemaUpdate = new SchemaUpdate();
			schemaUpdate.setHaltOnError(true);
			schemaUpdate.setFormat(true);
			schemaUpdate.setDelimiter(";");
			schemaUpdate.setOutputFile("gestorpublico.sql");
			
			// Executa no banco
			schemaUpdate.execute(EnumSet.of(TargetType.DATABASE), metadata);
			
			// Exibe no Console
			schemaUpdate.execute(EnumSet.of(TargetType.STDOUT), metadata);
			
			// Salva no arquivo
//			schemaUpdate.execute(EnumSet.of(TargetType.SCRIPT), metadata);

			System.out.println("Atualização finalizada!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
