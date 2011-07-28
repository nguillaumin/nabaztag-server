package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.violet.common.StringShop;
import net.violet.vadmin.objects.data.GetAdminData;
import net.violet.vadmin.objects.data.GetApplicationData;
import net.violet.vadmin.objects.data.HardwareData;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

public class ApplicationForm extends AdminForm {

	private static final long serialVersionUID = 1L;
	
	private String dispatch;
	private String display;
	
	private String cat;
	private String nameService;
	private String isVisible;
	private String name = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private String title = StringShop.EMPTY_STRING;
	private String description = StringShop.EMPTY_STRING;
	private String howTo = StringShop.EMPTY_STRING;
	private FormFile iconFile;
	private String iconFileId;
	private FormFile pictureFile;
	private String pictureFileId;
	private String[] languages = {};
	private String[] hardware = {};
	private GetAdminData appliInfo; 
	private List<HardwareData> hardwareList = new LinkedList<HardwareData>();

	private List<GetApplicationData> applicationList;
	private List<String> catList = new ArrayList<String>();

	protected ArrayList<LabelValueBean> namesList = new ArrayList<LabelValueBean>();
	private ArrayList<LabelValueBean> categoriesList = new ArrayList<LabelValueBean>();
	private ArrayList<LabelValueBean> trueFalse = new ArrayList<LabelValueBean>();

	public ArrayList<LabelValueBean> getTrueFalse() {
		this.trueFalse.add(new LabelValueBean("Yes", "true"));
		this.trueFalse.add(new LabelValueBean("No", "false"));
		return this.trueFalse;
	}

	public void setTrueFalse(ArrayList<LabelValueBean> inTrueFalse) {
		this.trueFalse = inTrueFalse;
	}
	
	public ArrayList<LabelValueBean> getNamesList() {
		this.namesList.add(new LabelValueBean("RSS", "net.violet.rss."));
		this.namesList.add(new LabelValueBean("Podcast", "net.violet.podcast."));
		this.namesList.add(new LabelValueBean("Webradio", "net.violet.webradio."));
		this.namesList.add(new LabelValueBean("Javascript", "net.violet.js."));
		this.namesList.add(new LabelValueBean("External", "net.violet.external."));
		return this.namesList;
	}
	
	public void setNamesList(ArrayList<LabelValueBean> inNamesList) {
		this.namesList = inNamesList;
	}
	
	public ArrayList<LabelValueBean> getCategoriesList() {
		for (final String inCategoryName : this.catList) {
			this.categoriesList.add(new LabelValueBean(inCategoryName, inCategoryName));
		}
		return categoriesList;
	}
	
	public void setCategoriesList(ArrayList<LabelValueBean> categoriesList) {
		this.categoriesList = categoriesList;
	}
	
	public List<String> getCatList() {
		return catList;
	}
	
	public void setCatList(List<String> catList) {
		this.catList = catList;
	}
	
	public FormFile getIconFile() {
		return iconFile;
	}
	
	public void setIconFile(FormFile iconFile) {
		this.iconFile = iconFile;
	}
	
	public FormFile getPictureFile() {
		return pictureFile;
	}
	
	public String getHowTo() {
		return howTo;
	}

	
	public void setHowTo(String howto) {
		this.howTo = howto;
	}

	public void setPictureFile(FormFile pictureFile) {
		this.pictureFile = pictureFile;
	}
	
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String inDisplay) {
		this.display = inDisplay;
	}

	public String getDispatch() {
		return this.dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getNameService() {
		return this.nameService;
	}

	public void setNameService(String nameService) {
		this.nameService = nameService;
	}

	public List<GetApplicationData> getApplicationList() {
		return this.applicationList;
	}

	public void setApplicationList(List<GetApplicationData> applicationList) {
		this.applicationList = applicationList;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}
	
	public GetAdminData getAppliInfo() {
		return appliInfo;
	}
	
	public void setAppliInfo(GetAdminData appliInfo) {
		this.appliInfo = appliInfo;
	}
	
	public String getCat() {
		return cat;
	}
	
	public void setCat(String category) {
		this.cat = category;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getUrl() {
		return url;
	}

	
	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getTitle() {
		return title;
	}

	
	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public String[] getLanguages() {
		return languages; 
	}

	
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	
	public String[] getHardware() {
		return hardware;
	}

	
	public void setHardware(String[] hardware) {
		this.hardware = hardware;
	}

	
	public List<HardwareData> getHardwareList() {
		return hardwareList;
	}
	
	@Override
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		super.reset(actionMapping, httpServletRequest);
		applicationList = ListUtils.lazyList(new LinkedList<GetApplicationData>(),
        new Factory() {
            public Object create() {
                return new GetApplicationData(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING);
            }
        });
    }

	
	public void setHardwareList(List<HardwareData> hardwareList) {
		this.hardwareList = hardwareList;
	}

	
	public String getIconFileId() {
		return iconFileId;
	}

	
	public void setIconFileId(String iconFileId) {
		this.iconFileId = iconFileId;
	}

	
	public String getPictureFileId() {
		return pictureFileId;
	}

	
	public void setPictureFileId(String pictureFileId) {
		this.pictureFileId = pictureFileId;
	}

	
}
