package net.violet.platform.datamodel;

import java.sql.Timestamp;
import java.util.Date;

import net.violet.db.records.Record;

/**
 * News for a Violet product. Unused ?
 * 
 *
 */
public interface News extends Record<News> {

	public Lang getNewsLang();

	public Product getNewsProduct();

	public String getTitle();

	public String getAbstract();

	public Files getPicture_s();

	public Files getPicture_b();

	public String getIntro();

	public String getBody();

	public Timestamp getDatePub();

	public Timestamp getDateExp();

	public void update(Lang reference, String inTitle, String inAbstract, Files inBig, Files inSmall, String inIntro, String inBody, Date inDatePub, Date inDateExp, Product inProduct);

}
