package org.gestorpublico.hibernate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.cfg.Environment;
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
			schemaExport.execute(EnumSet.of(TargetType.DATABASE), Action.CREATE, metadata);
			// Exibe no Console
			schemaExport.execute(EnumSet.of(TargetType.STDOUT), Action.CREATE, metadata);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
