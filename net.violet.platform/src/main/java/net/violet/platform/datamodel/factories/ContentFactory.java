package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface ContentFactory extends RecordFactory<Content>, FilesAccessor {

	/**
	 * Itérateur sur des objets déjà dans la base, à partir d'une condition.
	 * 
	 * @param inAction action considérée
	 * @param inSkip nombre de contenus à ne pas traiter.
	 * @param inWalker itérateur
	 * @return le nombre de contenus traités.
	 */
	int walkOlderByAction(VAction inAction, int inSkip, RecordWalker<Content> inContentWalker);

	/**
	 * Récupère les inNbContent {@link Content} pour l'action fournie
	 * 
	 * @param inAction
	 * @param inNbContent
	 * @param skipNewContent TODO
	 * @return la list des {@link Content}, une liste vide en cas de problème
	 */
	List<Content> findMostRecentsByAction(VAction inAction, int inNbContent, boolean skipNewContent);

	long insert(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml);

	long insert(VAction inAction, Files inFile, String inTitle);

	Content createNewContent(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml);

	List<Content> findUnreadByAction(VAction inAction, VObject inVObject, int inMaxContent);

	Content findOldestByAction(VAction inAction, int inOldestIndex);

	int walkWithoutAction(RecordWalker<Content> inContentWalker);
}
