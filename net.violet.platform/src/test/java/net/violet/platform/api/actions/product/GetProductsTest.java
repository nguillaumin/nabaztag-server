package net.violet.platform.api.actions.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.mock.ProductMock;

import org.junit.Assert;
import org.junit.Test;

public class GetProductsTest extends AbstractTestBase {

	static long i = 0L;

	private List<ProductMock> creerBDMock() {
		final List<ProductMock> resultList = new ArrayList<ProductMock>();
		resultList.add(new ProductMock(++GetProductsTest.i, "Corporate"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Nabaztag"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Mirror"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Ztamp"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Nanoztag"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Daldal"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Bookz"));
		resultList.add(new ProductMock(++GetProductsTest.i, "Earz"));

		return resultList;
	}

	
	@Test
	public void testGetCodes() throws APIException {
		final List<ProductMock> mocksList = creerBDMock();
		final Action theAction = new GetProducts();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final List<String> theResult = (List<String>) theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertEquals(GetProductsTest.i, theResult.size());
		final Iterator<ProductMock> i1 = mocksList.iterator();
		while (i1.hasNext()) {
			Assert.assertTrue(theResult.contains(i1.next().getName()));
		}
	}
}
