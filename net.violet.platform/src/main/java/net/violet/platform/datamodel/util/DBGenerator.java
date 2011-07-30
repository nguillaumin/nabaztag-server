package net.violet.platform.datamodel.util;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.db.records.associations.SingleAssociationNull;
import net.violet.platform.datamodel.MusicStyle;


/**
 * Try to retro-generate the database schema by looking
 * at the datamodel classes.
 * 
 * @author nabaztag-server-project
 *
 */
public class DBGenerator {
	
	private static final File root = new File("src/main/java");
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		
		// Find every data model class. This cannot be done using reflection
		// so we'll have to list the .java files
		Package dataModelPackage = net.violet.platform.datamodel.Agenda.class.getPackage();
		File dataModelDir = new File(root, dataModelPackage.getName().replace(".", "/"));
		
		System.out.println("-- Using datamodel directory '" + dataModelDir + "'");
		
		File[] candidates = dataModelDir.listFiles(new FilenameFilter() {
			public boolean accept(File f, String name) {
				return name.endsWith("Impl.java" );
			}
		});
		
		for (File f: candidates) {
			String className = f.getName().replace(".java", "");
			Class<? extends ObjectRecord> clazz = (Class<? extends ObjectRecord>) Class.forName(dataModelPackage.getName()+"."+className);
			System.out.println("-- Processing class '" + clazz.getName() + "'");
			generateTable(clazz);
		}

	}
	
	private static void generateTable(Class<?> clazz) throws Exception {
		// The table name is in the SPECIFICATION field, but for now it's
		// simpler this way.
		String tableName = getTableName(clazz.getSimpleName().replace("Impl", ""));
		// 

		// List of columns found
		List<String> columns = new ArrayList<String>();
		
		String primaryKey = null;
		boolean firstColumnField = true;		
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field f: fields) {
			if ("NEW_COLUMNS".equals(f.getName())) {
				continue;
			}
			
			if ("SPECIFICATION".equals(f.getName())) {
				// Extract the name of the table, ideally.
				continue;
			}
			
			// Ignore fields used for relation between Java objects
			if (SingleAssociationNotNull.class.equals(f.getType())
					|| MusicStyle.class.equals(f.getType())
					|| SingleAssociationNull.class.equals(f.getType())
					|| List.class.equals(f.getType())) {
				continue;
			}
			
			// Database mapped columns are usually lower case with underscore,
			// except for some cases...
			if (f.getName().matches("[a-z_]+")
					|| f.getName().contains("userFriendsAddress")) {

				// It's a database column
				columns.add(generateColumn(f, firstColumnField));
				if (firstColumnField) {
					primaryKey = "PRIMARY KEY (`"+f.getName()+"`)";
				}
				firstColumnField = false;
			}
		}
		
		// Generate SQL
		System.out.println("DROP TABLE IF EXISTS `" + tableName + "`;");
		System.out.println("CREATE TABLE `" + tableName + "` (");
		for (Iterator<String> it = columns.iterator(); it.hasNext(); ) {
			System.out.print(it.next());
			if (it.hasNext() || primaryKey != null) {
				System.out.print(",");
			}
			System.out.print("\n");
		}
		if (primaryKey != null) {
			System.out.println("  " + primaryKey);
		}
		System.out.println(");\n");
	}
	
	private static String generateColumn(Field f, boolean primary) {
		StringBuffer out = new StringBuffer();
		
		out.append(" `").append(f.getName());
		
		boolean isNumber = false;
		Class<?> type = f.getType();
		if (long.class.equals(type) || Long.class.equals(type)
				|| int.class.equals(type) || Integer.class.equals(type)) {
			out.append("` INT");
			isNumber = true;
		} else if (String.class.equals(type)) {
			out.append("` VARCHAR(255)");
		} else if (Timestamp.class.equals(type)) {
			out.append("` TIMESTAMP");
		} else if (Date.class.equals(type) || java.util.Date.class.equals(type)) {
			out.append("` DATE");
		} else if (Time.class.equals(type)) {
			out.append("` TIME");
		} else if (Boolean.class.equals(type) || boolean.class.equals(type)) {
			out.append("` TINYINT(1)");
		} else if (List.class.equals(type)) {
			out.append("-- Ignored List for column '" + f.getName() + "'\n");
		} else {
			throw new IllegalArgumentException("Unrecognized type '" + type.getName() + "'");
		}
		
		if (primary && isNumber) {
			out.append(" unsigned NOT NULL AUTO_INCREMENT");
		} 
		
		return out.toString();
	}
	
	/**
	 * Converts a CamelCase class name into a table_name.
	 * @param className
	 */
	private static String getTableName(String className) {
		StringBuffer out = new StringBuffer();
		for (int i=0; i<className.length(); i++) {
			char c = className.charAt(i);
			if (i == 0) {
				out.append(Character.toLowerCase(c));
			} else if (Character.isLowerCase(c)) {
				out.append(c);
			} else if (Character.isUpperCase(c)) {
				out.append("_").append(Character.toLowerCase(c));
			}
		}
		
		return out.toString();
	}

}
