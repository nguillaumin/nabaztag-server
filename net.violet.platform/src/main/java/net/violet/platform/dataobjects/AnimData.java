package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public final class AnimData extends APIData<Anim> {

	private static final Logger LOGGER = Logger.getLogger(AnimData.class);

	private final String animLabel;

	public static AnimData getData(Anim inAnim) {
		try {
			return RecordData.getData(inAnim, AnimData.class, Anim.class);
		} catch (final InstantiationException e) {
			AnimData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			AnimData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			AnimData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			AnimData.LOGGER.fatal(e, e);
		}

		return null;
	}

	private AnimData(Anim inAnim, Lang inIdLang) {
		super(inAnim);

		if ((inAnim != null) && (inIdLang != null) && (inAnim.getAnim_name() != null)) {
			this.animLabel = DicoTools.dico_if(inIdLang, inAnim.getAnim_name());
		} else {
			this.animLabel = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	protected AnimData(Anim inAnim) {
		super(inAnim);
		if ((inAnim != null) && (inAnim.getAnim_name() != null)) {
			this.animLabel = inAnim.getAnim_name();
		} else {
			this.animLabel = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	/**
	 * Accesseur sur toutes les animations pour une langue
	 */
	public static List<AnimData> findAllByLangOrderByName(Lang idLang) {
		final List<AnimData> theResult = new LinkedList<AnimData>();
		final List<Anim> theAnims = Factories.ANIM.findAllOrderByName();
		for (final Anim theAnim : theAnims) {
			theResult.add(new AnimData(theAnim, idLang));
		}
		return theResult;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return this.animLabel;
	}

	public long getId() {
		final Anim theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	public static List<AnimData> findAllAnim() {
		final List<AnimData> theResult = new LinkedList<AnimData>();
		for (final Anim theAnim : Factories.ANIM.findAllOrderByName()) {
			theResult.add(new AnimData(theAnim));
		}
		return theResult;
	}

	public String getName() {
		final Anim theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getAnim_name();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static AnimData findByAPIId(String inAPIId, String inAPIKey) {
		AnimData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.ANIM, inAPIKey);
		if (theID != 0) {
			final Anim theAnim = Factories.ANIM.find(theID);
			if (theAnim != null) {
				theResult = new AnimData(theAnim);
			}
		}
		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.ANIM;
	}

	public static AnimData findByName(String anim_name) {
		AnimData theResult = null;

		final Anim theAnim = Factories.ANIM.findByName(anim_name);
		if (theAnim != null) {
			theResult = new AnimData(theAnim);
		}
		return theResult;
	}
}
