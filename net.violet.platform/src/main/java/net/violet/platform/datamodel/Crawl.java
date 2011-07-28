package net.violet.platform.datamodel;

import net.violet.db.records.Record;

public interface Crawl extends Record<Crawl> {

	int CRAWL_TYPE_ID_METEO = 1;
	int CRAWL_TYPE_ID_BOURSE_BASIC = 2;
	int CRAWL_TYPE_ID_TRAFIC = 3;
	int CRAWL_TYPE_ID_AIR = 6;
	int CRAWL_TYPE_ID_BOURSE_ADVANCED = 20;

	String getCrawl_source();

	String getCrawl_param();

	void setCrawl_param(String inParam);
}
