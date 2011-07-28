package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface SignatureFactory extends RecordFactory<Signature>, FilesAccessor {

	Signature create(Files inFile, Anim inAnim, ColorType inColor);

	Signature get(Files inFile, Anim inAnim, ColorType inColor);

	/**
	 * Finds by {@link Files}, {@link Anim} & {@link ColorType}
	 * 
	 * @param inFile
	 * @param inAnim
	 * @param inColor
	 * 
	 * @return the {@link Signature} or <code>null</code> if it does not exist.
	 */

	Signature find(Files inFile, Anim inAnim, ColorType inColor);

}
