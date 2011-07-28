package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyTerrierFriendsForm;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.FriendsListsImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.BlackMock;
import net.violet.platform.datamodel.mock.FriendsListsMock;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;
import net.violet.platform.web.ServletTestBase;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessages;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests pour MySrvRssAction.
 */
public class MyTerrierFriendsActionTest extends ActionTestBase {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyTerrierFriendsActionTest.class);

	/**
	 * If we cannot find the user in the session we send him/her back to the the
	 * login page
	 */
	@Test
	public void testEmptySessionRedirect() {
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "load", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "deleteFriends", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "addBlackList", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "acceptFriend", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "acceptFriendAdd", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "declineFriend", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "cancelFriend", ActionTestBase.FORWARD_LOGIN);
		ActionTestBase.testRedirectEmptySession(MyTerrierFriendsAction.class, MyTerrierFriendsForm.class, "addFriend", ActionTestBase.FORWARD_LOGIN);
	}

	/**
	 * Test the deletion of a friend
	 * 
	 */
	// TODO : appel BD via constructeur
	// @Test Voir les singleAssociation
	public void deleteFriend() {
		final MyTerrierFriendsAction myAction = new MyTerrierFriendsAction();
		final MyTerrierFriendsForm myForm = new MyTerrierFriendsForm();
		final User myUser = getActionTestUser();
		final HttpSession session = ActionTestBase.createUserSession(myUser);
		final HttpServletRequest request = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		final List<UserData> listFriends = new ArrayList<UserData>();
		listFriends.add(UserData.getData(myUser));
		myForm.setListFriends(listFriends);

		Assert.assertTrue(0 != myForm.getListFriends().size());

		myAction.deleteFriends(ActionTestBase.createMapping(), myForm, request, myResponse);

		Assert.assertTrue(0 == myForm.getListFriends().size());
	}

	/**
	 * Test the addition of a friend
	 * 
	 */
	// TODO : appel BD via constructeur
	// @Test Voir les singleAssociation
	public void acceptFriendAdd() {
		final MyTerrierFriendsAction myAction = new MyTerrierFriendsAction();
		final MyTerrierFriendsForm myForm = new MyTerrierFriendsForm();
		final User myUser = getActionTestUser();
		final HttpSession session = ActionTestBase.createUserSession(myUser);
		final HttpServletRequest request = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();

		myForm.setFriend_id(myUser.getId().intValue());

		Assert.assertTrue(0 == myForm.getListFriends().size());

		myAction.acceptFriendAdd(ActionTestBase.createMapping(), myForm, request, myResponse);

		Assert.assertTrue(0 != myForm.getListFriends().size());
	}

	/**
	 * Test the addition of self and it does not work
	 * 
	 */
	// TODO : appel BD via constructeur
	// @Test Voir les singleAssociation
	public void addSelf() {
		final MyTerrierFriendsAction myAction = new MyTerrierFriendsAction();
		final MyTerrierFriendsForm myForm = new MyTerrierFriendsForm();
		final User myUser = getActionTestUser();
		final HttpSession session = ActionTestBase.createUserSession(myUser);
		final HttpServletRequest request = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		try {
			new FriendsListsImpl(myUser.getId(), 0, 0, 0);
		} catch (final SQLException e) {
			MyTerrierFriendsActionTest.LOGGER.fatal(e, e);
		}
		myForm.setName("ActionTestUser");

		Assert.assertTrue(0 == myForm.getListFriends().size());

		myAction.addFriend(ActionTestBase.createMapping(), myForm, request, myResponse);

		Assert.assertTrue(0 == myForm.getListFriends().size());

	}

	/**
	 * Test the addition of a friend and the user ends up in the waiting list
	 * 
	 */
	// TODO : appel BD via constructeur
	// @Test Voir les singleAssociation
	public void addFriend() {
		final MyTerrierFriendsAction myAction = new MyTerrierFriendsAction();
		final MyTerrierFriendsForm myForm = new MyTerrierFriendsForm();
		final User myUser = getActionTestUser();
		final User myUser2 = getActionTestUser2();
		final HttpSession session = ActionTestBase.createUserSession(myUser);
		final HttpServletRequest request = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		try {
			new FriendsListsImpl(myUser.getId(), 0, 0, 0);
		} catch (final SQLException e) {
			MyTerrierFriendsActionTest.LOGGER.fatal(e, e);
		}
		try {
			new FriendsListsImpl(myUser2.getId(), 5, 0, 0);
		} catch (final SQLException e) {
			MyTerrierFriendsActionTest.LOGGER.fatal(e, e);
		}

		Assert.assertTrue(0 == myForm.getListFriends().size());

		myForm.setName("ActionTestUser2");
		myAction.addFriend(ActionTestBase.createMapping(), myForm, request, myResponse);

		Assert.assertTrue(0 == myForm.getListFriends().size());

		Assert.assertTrue(myUser2.getFriends().contains(myUser));
		Assert.assertTrue(myUser.getFriends().contains(myUser2));
	}

	/**
	 * Test the addition of a friend and the user is actually blacklisted
	 * 
	 */
	// TODO : appel BD via constructeur
	// @Test Voir les singleAssociation
	public void addFriendBlackListed() {
		final MyTerrierFriendsAction myAction = new MyTerrierFriendsAction();
		final MyTerrierFriendsForm myForm = new MyTerrierFriendsForm();
		final User myUser = getActionTestUser();
		final User myUser2 = getActionTestUser2();
		final HttpSession session = ActionTestBase.createUserSession(myUser);
		final HttpServletRequest request = ServletTestBase.createRequest(session);
		final HttpServletResponse myResponse = ServletTestBase.createResponse();
		new FriendsListsMock(myUser.getId(), 0, 0, 0);
		new FriendsListsMock(myUser2.getId(), 5, 0, 0);

		final Black black = new BlackMock(myUser, myUser2, StringShop.EMPTY_STRING);
		myUser.getBlackList().put(myUser2, black);

		Assert.assertTrue(0 == myForm.getListFriends().size());

		myForm.setName("ActionTestUser2");
		myAction.addFriend(ActionTestBase.createMapping(), myForm, request, myResponse);

		Assert.assertTrue(0 == myForm.getListFriends().size());

		Assert.assertFalse(myUser2.getFriends().contains(myUser));
		Assert.assertFalse(myUser.getFriends().contains(myUser2));

		final Object myObject = request.getAttribute(Globals.ERROR_KEY);

		if (myObject instanceof ActionMessages) {
			final ActionMessages errors = (ActionMessages) myObject;
			Assert.assertNotNull(errors.get("blackedUser"));
		} else {
			Assert.assertTrue(false);
		}
	}
}
