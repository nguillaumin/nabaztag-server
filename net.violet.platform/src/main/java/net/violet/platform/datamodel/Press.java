package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Manages press news / releases about Violet objects. Unused ?
 *
 */
public interface Press extends Record<Press> {

	public Lang getPressLang();

	public Product getPressProduct();

	public String getTitle();

	public String getAbstract();

	public Files getPicture();

	public String getUrl();

	public void update(Lang inLang, String inTitle, String inAbstract, Files inPicture, String inUrl, Product inProduct);

}
