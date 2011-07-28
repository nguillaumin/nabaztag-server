package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.factories.Factories;

public class NathanTagData extends RecordData<NathanTag> {

	public NathanTagData(NathanTag inTag) {
		super(inTag);
	}

	/**
	 * Returns a NathanTagData object found with the provided id.
	 * 
	 * @param id
	 * @return a NathanTagData object
	 */
	public static NathanTagData find(long id) {
		final NathanTag tag = Factories.NATHAN_TAG.find(id);
		return new NathanTagData(tag);
	}

	/**
	 * Returns a map containing all available tags. The map is used to sort the
	 * tags. Map keys are integer describing the categories and map values are
	 * list of tags.
	 * 
	 * @return a map object, can be empty but not null.
	 */
	public static Map<Integer, List<NathanTagData>> findAllTagsSorted() {
		final Map<Integer, List<NathanTagData>> theResult = new TreeMap<Integer, List<NathanTagData>>();

		for (final Entry<Integer, List<NathanTag>> entry : Factories.NATHAN_TAG.findAllTagsSorted().entrySet()) {
			theResult.put(entry.getKey(), NathanTagData.generateList(entry.getValue()));
		}

		return theResult;
	}

	/**
	 * Returns all tags matching the provided category.
	 * 
	 * @param categId category id
	 * @return
	 */
	public static List<NathanTagData> findTagsByCategory(int categId) {
		return NathanTagData.generateList(Factories.NATHAN_TAG.findAllTagsByCategory(categId));
	}

	/**
	 * Convinient method to convert a list of NathanTag objects into a list of
	 * NathanTagData objects. This method has a package visibility to allow
	 * access from the class NathanVersionData. If the provided list is null the
	 * method returns an empty list (not null).
	 * 
	 * @param inList the list to convert
	 * @return a list of NathanTagData built according to the provided list.
	 */
	static List<NathanTagData> generateList(List<NathanTag> inList) {

		if (inList != null) {
			final List<NathanTagData> result = new ArrayList<NathanTagData>();

			for (final NathanTag tag : inList) {
				result.add(new NathanTagData(tag));
			}

			return result;
		}

		return Collections.emptyList();
	}

	public Long getId() {
		final NathanTag theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0L;
	}

	/**
	 * Returns the category id.
	 * 
	 * @return the category id, -1 if there is no value.
	 */
	public long getCateg() {
		final NathanTag theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getCateg();
		}

		return -1;
	}

	public String getLabel() {
		final NathanTag theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getLabel();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

}
