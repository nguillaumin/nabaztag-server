package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Choregraphies (Colour, sounds) 
 *
 */
public interface Anim extends Record<Anim> {

	String getAnim_name();

	String getAnim_signature();

	String getAnim_url();
}
