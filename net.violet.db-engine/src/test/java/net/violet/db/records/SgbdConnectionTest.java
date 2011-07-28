package net.violet.db.records;

import junit.framework.Assert;
import net.violet.db.connector.Connector;
import net.violet.db.connector.Connector.ConnectionMode;

import org.junit.Test;

public class SgbdConnectionTest {

	@Test
	public void testCreateConnection() {
		SgbdConnection theConnection = new SgbdConnection(ConnectionMode.TEST_DEBUG, SgbdConnection.SGBD_ACCESS.READONLY);
		Assert.assertEquals(1, Connector.getInstanceMap().size());

		//check if we not open another connector
		theConnection = new SgbdConnection(ConnectionMode.TEST_DEBUG, SgbdConnection.SGBD_ACCESS.READONLY);
		Assert.assertEquals(1, Connector.getInstanceMap().size());
		theConnection.close();

		//check connector in read only  
		Assert.assertNull(Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).get(Connector.ACCESS_MODE.WRITE));
		Assert.assertNotNull(Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).get(Connector.ACCESS_MODE.READ));

		//check if we not open another connector
		Assert.assertEquals(1, Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).size());
		theConnection = new SgbdConnection(ConnectionMode.TEST_DEBUG, SgbdConnection.SGBD_ACCESS.WRITEONLY);
		Assert.assertEquals(1, Connector.getInstanceMap().size());

		//check connector in write only
		theConnection = new SgbdConnection(ConnectionMode.TEST_DEBUG, SgbdConnection.SGBD_ACCESS.WRITEONLY);
		Assert.assertEquals(1, Connector.getInstanceMap().size());
		theConnection.close();

		//check opening of the connector in read and write
		Assert.assertEquals(2, Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).size());
		Assert.assertEquals(Connector.ACCESS_MODE.READ.getUrl(), Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).get(Connector.ACCESS_MODE.READ).getAccessMode().getUrl());
		Assert.assertEquals(Connector.ACCESS_MODE.WRITE.getUrl(), Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).get(Connector.ACCESS_MODE.WRITE).getAccessMode().getUrl());

		//Check get connector in InstanceMap
		theConnection = new SgbdConnection(ConnectionMode.TEST_DEBUG, SgbdConnection.SGBD_ACCESS.READWRITE);
		Assert.assertEquals(1, Connector.getInstanceMap().size());
		Assert.assertEquals(2, Connector.getInstanceMap().get(ConnectionMode.TEST_DEBUG).size());

		//check opening another connector
		final SgbdConnection theConnectionJabber = new SgbdConnection(ConnectionMode.EJABBERD, SgbdConnection.SGBD_ACCESS.READONLY);
		Assert.assertEquals(2, Connector.getInstanceMap().size());
		theConnectionJabber.close();

		//check pool not initialized
		Assert.assertNull(Connector.getInstanceMap().get(ConnectionMode.POOL_INITIALIZED));

		//check opening of the pool connector 
		SgbdConnection theDefaultConnection = new SgbdConnection();
		Assert.assertEquals(3, Connector.getInstanceMap().size());

		//check if pool by default is initialized in read and write
		Assert.assertEquals(2, Connector.getInstanceMap().get(ConnectionMode.DEFAULT).size());
		Assert.assertEquals(ConnectionMode.POOL_INITIALIZED, Connector.getInstanceMap().get(ConnectionMode.DEFAULT).get(Connector.ACCESS_MODE.READ).getConnectionMode());
		Assert.assertEquals(ConnectionMode.POOL_INITIALIZED, Connector.getInstanceMap().get(ConnectionMode.DEFAULT).get(Connector.ACCESS_MODE.WRITE).getConnectionMode());

		//check if we not open another pool by default
		theDefaultConnection = new SgbdConnection();
		Assert.assertEquals(3, Connector.getInstanceMap().size());
		theDefaultConnection.close();

		//check read en write connector
		Assert.assertEquals(Connector.ACCESS_MODE.READ.getUrl(), Connector.getInstanceMap().get(ConnectionMode.DEFAULT).get(Connector.ACCESS_MODE.READ).getAccessMode().getUrl());
		Assert.assertEquals(Connector.ACCESS_MODE.WRITE.getUrl(), Connector.getInstanceMap().get(ConnectionMode.DEFAULT).get(Connector.ACCESS_MODE.WRITE).getAccessMode().getUrl());

		//opening pool in read only ( not default connector)
		final SgbdConnection thePoolConnection = new SgbdConnection(ConnectionMode.POOL, SgbdConnection.SGBD_ACCESS.READONLY);
		Assert.assertEquals(4, Connector.getInstanceMap().size());

		//check if pool has open one connector
		Assert.assertEquals(1, Connector.getInstanceMap().get(ConnectionMode.POOL).size());

		//check if pool is initialized and is in read only 
		Assert.assertEquals(ConnectionMode.POOL_INITIALIZED, Connector.getInstanceMap().get(ConnectionMode.POOL).get(Connector.ACCESS_MODE.READ).getConnectionMode());
		thePoolConnection.close();
		Assert.assertEquals(Connector.ACCESS_MODE.READ.getUrl(), Connector.getInstanceMap().get(ConnectionMode.POOL).get(Connector.ACCESS_MODE.READ).getAccessMode().getUrl());
		Assert.assertNull(Connector.getInstanceMap().get(ConnectionMode.POOL).get(Connector.ACCESS_MODE.WRITE));

	}
}
