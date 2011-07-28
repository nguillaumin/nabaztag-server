package net.violet.db.utils.test;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import net.violet.common.StringShop;
import net.violet.common.utils.io.StreamTools;
import net.violet.db.connector.Connector;
import net.violet.db.records.SgbdConnection;

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
public abstract class DBTest {

	protected void setConnection() {
		SgbdConnection.setConnectionMode(Connector.ConnectionMode.TEST_DEBUG);
	}

	protected void loadSQL(URL inURL) throws SQLException, IOException {
		final SgbdConnection theConnection = new SgbdConnection();
		final String file = StreamTools.readString(inURL);
		for (final String input : file.split(";\r?\n")) {
			final String theLine = input.trim();
			if (!theLine.equals(StringShop.EMPTY_STRING)) {
				final int theResult = theConnection.doQueryUpdate(theLine);
				if (theResult < 0) {
					throw new SQLException("Erreur avec la requête " + theLine);
				}
			}
		}
		theConnection.close();
	}
}
