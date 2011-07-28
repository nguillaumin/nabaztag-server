package net.violet.vadmin.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.vadmin.objects.data.GetPressData;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;


public class AdminSearchPressForm extends AdminForm {


	private static final long serialVersionUID = 1L;
	
	private String productPress;
	private String dispatch;
	private String display;
	private String idPress;
	private String title;
	private String summary;
	private String url;
	private FormFile imageFile;
	private String imagePath;

	private GetPressData thePressData;
	private List<GetPressData> pressList = Collections.emptyList();
	private List<String> productList = Collections.emptyList();
	private ArrayList<LabelValueBean> namesList = new ArrayList<LabelValueBean>();

	public String getProductPress() {
		return this.productPress;
	}

	public void setProductPress(String productPress) {
		this.productPress = productPress;
	}

	public List<GetPressData> getPressList() {
		return this.pressList;
	}

	public void setPressList(List<GetPressData> pressList) {
		this.pressList = pressList;
	}

	public String getDispatch() {
		return this.dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public List<String> getProductList() {
		return this.productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	public ArrayList<LabelValueBean> getNamesList() {
		for (final String inProductName : this.productList) {
			this.namesList.add(new LabelValueBean(inProductName, inProductName));
		}
		return this.namesList;
	}

	public void setNamesList(ArrayList<LabelValueBean> inNamesList) {
		this.namesList = inNamesList;
	}

	public String getIdPress() {
		return idPress;
	}

	public void setIdPress(String idPress) {
		this.idPress = idPress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public GetPressData getThePressData() {
		return thePressData;
	}

	public void setThePressData(GetPressData thePressData) {
		this.thePressData = thePressData;
	}

	public FormFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
