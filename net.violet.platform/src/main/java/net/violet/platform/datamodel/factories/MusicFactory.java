package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface MusicFactory extends RecordFactory<Music>, FilesAccessor {

	/**
	 * Récupération de toutes les musiques qui conviennent pour la signature.
	 * 
	 * @param inUser l'user.
	 * @return les objets music associés à cet user + les signatures (accordéon,
	 *         etc.).
	 */
	@Deprecated
	List<Music> findAllForSignature(User inUser);

	List<Music> findAllByUserIdAndType(User inOwner, String ensMimeTypes, int skip, int count);

	List<Music> findRecentsByType(String ensebmle, int count);

	long countItemsOfUserByMimeType(User inOwner, String mimeType);

	Music createNewMusic(User theUser, Files inFile) throws BadMimeTypeException;

	/**
	 * fonction qui ajoute une nouvelle musique dans la base
	 * 
	 * @param inFile
	 * @param name : nom de la musique
	 * @param inOwner : utilisateur
	 * @param styleid : music style id (voir dans la table musicstyle)
	 * @param share : 1 pour partag la musique et 0 sinon
	 * @return null sinon Music
	 */

	Music createNewMusic(Files inFile, String name, User inOwner, int styleid, int share);

	Music create(Files inFile, Lang inLang, String name, User inOwner, int styleid, int share, int type);

	/**
	 * @return les sons de l'utilisateur
	 */
	List<Music> findAllPersoByUser(User inUser);

	/**
	 * @return tout les sons du reveil associée à la langue
	 */
	List<Music> findAllForAlarmClock(Lang inLang);

	/**
	 * @return les sons de la bibliothèque associée à la catégorie
	 */
	List<Music> findAllForBiblio(int inMusicStyleId);

	/**
	 * @return les sons associée à la catégorie
	 */
	List<Music> findByStyle(long inMusicStyleId);

	/**
	 * @return tout les sons partagés( nabshare) d'un utilisateur
	 */
	List<Music> findAllNabshareByOwner(User user);

	/**
	 * @return tout les sons partagés (nabshare) associé au tag et à la langue
	 */
	List<Music> findByTagAndLang(String inTag, Lang inLang);

	/**
	 * @return un son pris aléatoirement dans biblio
	 */
	Music findRandomInBiblio();

	/**
	 * @return un son pris aléatoirement dans le nabshare et filtré par langue
	 */
	Music findNabshareRand(Lang inLang);

	/**
	 * @return un son pris aléatoirement dans le nabshare
	 */
	Music findNabshareRand();

	/**
	 * @return un son d'un utilisateur, ou un son partagé ou un son de la
	 *         biblio, clin
	 */
	Music findByIdOrUser(long id, User user);

	/**
	 * @return un son pris aléatoirement dans les clin d'oeil et une sous
	 *         catégorie de celle ci
	 */
	Music findRandomClin(long idCateg, Lang inLang);

	/**
	 * @return un son pris aléatoirement dans les clin d'oeil
	 */
	Music findRandomClin(Lang lang);

	/**
	 * @return tout les sons des clin d'oeil filtré par une sous catégorie de
	 *         celle ci
	 */
	List<Music> findAllClin(Lang inLang, long idCateg);

	/**
	 * @return tout les sons partagés (nabshare) associé à la catégorie et à la
	 *         langue
	 */
	List<Music> findByNabshareCategAndLang(long idNabshareCateg, Lang inLang);

	/**
	 * @return la musique grâce au Files
	 */
	Music findByFile(Files inFile);

	/**
	 * @return la musique pour une langue et un type donnés
	 */
	List<Music> findAllStylesByTypeAndLang(long type, Lang langReference, String categoryName, int skip, int count);

	/**
	 * @return la musique pour une categorié donnés
	 */
	List<Music> findByNabshareCategName(String categoryName, int skip, int count);

	/**
	 *  
	 * @return liste des signatures proposé par l'OS
	 */
	List<Music> findAllSignatures();

}
