package net.violet.vadmin.forms;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.violet.commondev.utils.StringShop;
import net.violet.vadmin.objects.data.DicoData;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public final class AdminDicoForm extends AdminForm{

	private static final long serialVersionUID = 1L;

	private String dispatch;
	private String display;

	private String id;
	private String dicoName = StringShop.EMPTY_STRING;
	private String url = StringShop.EMPTY_STRING;
	private List<DicoData> values;
	private FormFile importFile;
	
	public String getDispatch() {
		return dispatch;
	}
	
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}
	
	public String getDicoName() {
		return dicoName;
	}
	
	public void setDicoName(String dicoName) {
		this.dicoName = dicoName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<DicoData> getValues() {
		return values;
	}
	
	public void setValues(List<DicoData> inValues) {
		this.values = inValues;
		
	}
	
	@Override
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		super.reset(actionMapping, httpServletRequest);
        values = ListUtils.lazyList(new LinkedList<DicoData>(),
        new Factory() {
            public Object create() {
                return new DicoData(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING);
            }
        });
    }

	
	public String getDisplay() {
		return display;
	}

	
	public void setDisplay(String display) {
		this.display = display;
	}

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	
	public FormFile getImportFile() {
		return importFile;
	}

	
	public void setImportFile(FormFile importFile) {
		this.importFile = importFile;
	}
	
}
