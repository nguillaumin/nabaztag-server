package net.violet.platform.datamodel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import net.violet.db.cache.Cache;

import org.junit.After;
import org.junit.Before;

/**
 * Classe pour les tests qui permet de charger des requêtes SQL.
 * 
 * Commande à exécuter pour les insert: mysqldump -u dev -p -t --compact user -w
 * 'user_pseudo = "violet22"'
 * 
 * Pour les SELECT imbriqués, il faut locker les tables utilisées ou plus simple
 * faire un lock sur la DB: option "-x"
 * 
 * ex: mysqldump -u dev -p123 -t --compact -x bdZone files -w 'id IN (SELECT
 * file_id FROM content WHERE action_id = 80)'
 * 
 * Pour que les classes de test puisse prendre en compte le chargement de
 * données et le clean il faut l'étendre de cette classe
 * 
 * Les fichiers doivent être placés au même endroit que les fichiers java de
 * test
 * 
 * Leurs noms doit commencer par le nom de la classe de test suivi de
 * _setup_core.sql et/ou _setup_zone.sql idem pour le clean _teardown_core.sql
 * et/ou _teardown_zone.sql
 * 
 * ex : UserTest.java => UserTest_setup_core.sql
 * 
 * Les fichiers ne doivent pas contenir de commentaires et les requêtes doivent
 * se finir par un ; suivi d'un retour chariot
 * 
 */
public abstract class DBTest extends net.violet.db.utils.test.DBTest {

	@Before
	public void setupDB() throws SQLException, IOException {
		setConnection();
		loadSQL(getClass(), "_setup.sql");
		loadSQL(DBTest.class, "_MemoryTables_setup.sql");
	}

	@After
	public void tearDownDB() throws SQLException, IOException {
		setConnection();
		loadSQL(getClass(), "_teardown.sql");
		Cache.clearForTest();
		System.gc();
	}

	private void loadSQL(Class inClass, String inSuffix) throws SQLException, IOException {
		final String theSQLFileName = inClass.getName().replaceAll("\\.", "/") + inSuffix;
		final URL theSQLFileURL = ClassLoader.getSystemResource(theSQLFileName);
		if ((theSQLFileURL != null) && !net.violet.common.StringShop.EMPTY_STRING.equals(theSQLFileName)) {
			loadSQL(theSQLFileURL);
		} else {
			System.out.println("Warning: fichier " + theSQLFileName + " inexistant");
		}
	}
}
