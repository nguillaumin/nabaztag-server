package net.violet.platform.datamodel.factories.mock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.factories.AnimFactory;
import net.violet.platform.datamodel.mock.AnimMock;

public class AnimFactoryMock extends RecordFactoryMock<Anim, AnimMock> implements AnimFactory {

	private static final Comparator<Anim> ORDER_BY_NAME = new Comparator<Anim>() {

		public int compare(Anim o1, Anim o2) {
			return o1.getAnim_name().compareTo(o2.getAnim_name());
		}

	};

	@Override
	public void loadCache() {
		AnimMock.BUILDER.generateValuesFromInitFile(4, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/animInit");
	}

	public AnimFactoryMock() {
		super(AnimMock.class);
	}

	public List<Anim> findAllOrderByName() {
		final List<Anim> theList = findAll();
		Collections.sort(theList, AnimFactoryMock.ORDER_BY_NAME);
		return theList;
	}

	public Anim findByName(String anim_name) {
		for (final Anim anAnimation : findAll()) {
			if (anAnimation.getAnim_name().equals(anim_name)) {
				return anAnimation;
			}
		}
		return null;
	}

}
