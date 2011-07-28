package net.violet.platform.datamodel;

import java.util.List;

import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.MailTools;

import org.junit.Assert;
import org.junit.Test;

public class AnimTest extends DBTest {

	@Test
	public void testFindAllOrderByName() {
		final List<Anim> theAnims = Factories.ANIM.findAllOrderByName();
		Assert.assertEquals(17, theAnims.size());
		final Anim theFirstAnim = theAnims.get(0);
		Assert.assertEquals("LOC_anim/attention", theFirstAnim.getAnim_name());
		Assert.assertEquals("broadcast/broad/config/anim/sig_attention.chor", theFirstAnim.getAnim_url());
		Assert.assertEquals("long,3,none,nomusic.mid,0,7,0,led,4,RRR,VVV,BBB,0,led,3,RRR,VVV,BBB,0,led,0,RRR,VVV,BBB,1,led,3,0,0,0,1,led,2,RRR,VVV,BBB,2,led,2,0,0,0,2,led,1,RRR,VVV,BBB,3,led,4,0,0,0,3,led,1,0,0,0,3,led,2,RRR,VVV,BBB,4,led,2,0,0,0,4,led,3,RRR,VVV,BBB,5,led,3,0,0,0,5,led,2,RRR,VVV,BBB,6,led,2,0,0,0,6,led,4,RRR,VVV,BBB,6,led,1,RRR,VVV,BBB,7,led,1,0,0,0,7,led,2,RRR,VVV,BBB,8,led,2,0,0,0,8,led,3,RRR,VVV,BBB,8,led,2,RRR,VVV,BBB,8,led,1,RRR,VVV,BBB,9,led,4,0,0,0,9,led,3,0,0,0,9,led,2,0,0,0,9,led,1,0,0,0,9,led,0,0,0,0", theFirstAnim.getAnim_signature());
	}

	// @Test
	public void testMail() {
		MailTools.checkNbMail("pop.gmail.com", "violet.servicecheck", "violet123", "pop");

	}
}
