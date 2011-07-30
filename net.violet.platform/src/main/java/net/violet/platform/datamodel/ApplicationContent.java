package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Application content (images, sounds, etc)
 * 
 *
 */
public interface ApplicationContent extends Record<ApplicationContent> {

	Application getApplication();

	Files getFiles();

}
