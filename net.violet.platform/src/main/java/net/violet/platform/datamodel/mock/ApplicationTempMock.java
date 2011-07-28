package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTemp;

public class ApplicationTempMock extends AbstractMockRecord<ApplicationTemp, ApplicationTempMock> implements ApplicationTemp {

	private final String mApplicationName;
	private String shortcut;
	private String link;
	private String picture;
	private String icon;

	protected ApplicationTempMock(String inApplicationName) {
		super(0);
		this.mApplicationName = inApplicationName;
		this.shortcut = this.mApplicationName + "_shortcut";
		this.link = this.mApplicationName + "_link2";
	}

	public ApplicationTempMock(Application application, String inLink, String inShortcut) {
		super(application.getId());
		this.mApplicationName = application.getName();
		this.shortcut = inShortcut;
		this.link = inLink;
	}

	public ApplicationTempMock(Application theApplication, String inLink, String thePicture, String theIcon, String inShortcut) {
		super(theApplication.getId());
		this.mApplicationName = theApplication.getName();
		this.shortcut = inShortcut;
		this.link = inLink;
		this.picture = thePicture;
		this.icon = theIcon;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getIcone() {
		return this.icon;
	}

	public String getImage() {
		return this.picture;
	}

	public String getLink() {
		return this.link;
	}

	public String getShortcut() {
		return this.shortcut;
	}

	public Application getApplication() {
		throw new UnsupportedOperationException();
	}

	public long getType() {
		return 0;
	}

	public void setLink(String inLink) {
		this.link = inLink;
	}

	public void setShorcut(String inShorcut) {
		this.shortcut = inShorcut;
	}
}
