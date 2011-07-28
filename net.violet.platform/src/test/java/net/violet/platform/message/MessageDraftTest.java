package net.violet.platform.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.MessageServices.Body;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.elements.SequencePart;

import org.junit.Assert;
import org.junit.Test;

public class MessageDraftTest extends MockTestBase {

	@Test
	public void addAudioShouldStreamTest() {
		final String url = "I'll be back!";
		final boolean stream = true;
		final boolean withEar = true;

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addAudio(url, stream, withEar);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_MUSICSHOULDSTREAM, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addAudioDownloadTest() {
		final String url = "I'll be back!";
		final boolean stream = false;
		final boolean withEar = true;

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addAudio(url, stream, withEar);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_MUSICDOWNLOAD, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addAudioMusicStreamingTest() {
		final String url = "I'll be back!";
		final boolean stream = true;
		final boolean withEar = false;

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addAudio(url, stream, withEar);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_MUSIC_STREAMING, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addAudioStreamSecureTest() {
		final String url = "For your eyes only";
		final long position = 2;

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addAudioStreamSecure(url, position);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();

		Assert.assertEquals(Sequence.SEQ_SECURESTREAMING, type);
		// Can not check the data
	}

	@Test
	public void addChoregraphyDownloadTest() {
		final String url = "Tonight... we dine... in HELL !!";

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addChoreography(url, false);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());

		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_CHORDOWNLOAD, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addChoregraphyStreamingTest() {
		final String url = "Tonight... we dine... in HELL !!";

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addChoreography(url, true);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());

		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_CHORSTREAMING, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addPaletteTest() {

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addPalette(Palette.EMOTION);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_PALETTE, type);
		Assert.assertEquals(Long.toString(Palette.EMOTION.getInternalValue()), val);
	}

	@Test
	public void addStartInteractiveTest() throws UnsupportedEncodingException {

		final VObject theObject = getPrivateObject();
		final VObject theRfid = new VObjectMock(18L, "serial", net.violet.common.StringShop.EMPTY_STRING, theObject.getOwner(), HARDWARE.BOOK, null, null);
		final Application theApplication = new ApplicationMock(17L, "myAppli", theObject.getOwner(), Calendar.getInstance().getTime());
		final MessageDraft theMessage = new MessageDraft(theObject);
		theMessage.setApplication(theApplication);

		theMessage.addStartInteractive(theRfid);

		final String url = URLEncoder.encode(theApplication.getName(), "UTF-8") + "&zn=" + theRfid.getObject_serial();

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_BEGIN_INTERACTIVE_MODE, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addStartInteractiveWithUrlTest() {
		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);
		final String url = "theUrl";

		theMessage.addStartInteractive(url);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_BEGIN_INTERACTIVE_MODE, type);
		Assert.assertEquals(url, val);
	}

	@Test
	public void addEndInteractiveTest() {

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addEndInteractive();

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_END_INTERACTIVE_MODE, type);
		Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING, val);
	}

	@Test
	public void addStreamIdTest() {
		final String streamId = "2";

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addStreamId(streamId);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_STREAMING_ID, type);
		Assert.assertEquals(streamId, val);
	}

	@Test
	public void addStreamStopTest() {
		final String streamId = "2";

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addStreamStop(streamId);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_STREAMING_STOP, type);
		Assert.assertEquals(streamId, val);
	}

	@Test
	public void addSettingTest() {
		final String setting = "setting=value";

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addSetting(setting);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_SETTING, type);
		Assert.assertEquals(setting, val);
	}

	@Test
	public void addWaitMsTest() {
		final int time = 10000;

		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);

		theMessage.addWaitMs(time);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(1, list.size());
		final int type = list.get(0).getType();
		final String val = list.get(0).getData();

		Assert.assertEquals(Sequence.SEQ_WAIT, type);
		Assert.assertEquals(net.violet.common.StringShop.EMPTY_STRING + time, val);
	}

	@Test
	public void addSignatureTest() {
		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);
		final Anim theAnim = Factories.ANIM.findAllOrderByName().get(0);
		final Music theMusic = new MusicMock(1L, "name", new FilesMock("/home/agata/Desktop/sons/1", MimeType.MIME_TYPES.A_MPEG), null, 7, 0, 0, 0);
		final MessageSignature theSignature = new MessageSignature(5, theAnim, theMusic);

		theMessage.addSignature(theSignature);

		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(6, list.size());

		Sequence seq = list.get(0);
		Assert.assertEquals(Sequence.SEQ_MUSICSIGN, seq.getType());
		Assert.assertEquals(theSignature.getIntroMusicUrl(), seq.getData());

		seq = list.get(1);
		Assert.assertEquals(Sequence.SEQ_COLOR, seq.getType());
		Assert.assertEquals(Integer.toString(theSignature.getColor4Message()), seq.getData());

		seq = list.get(2);
		Assert.assertEquals(Sequence.SEQ_CHORDOWNLOAD, seq.getType());
		Assert.assertEquals(theSignature.getIntroChoregraphyUrl(), seq.getData());

		seq = list.get(3);
		Assert.assertEquals(Sequence.SEQ_MUSICSIGN, seq.getType());
		Assert.assertEquals(theSignature.getOutroMusicUrl(), seq.getData());

		seq = list.get(4);
		Assert.assertEquals(Sequence.SEQ_COLOR, seq.getType());
		Assert.assertEquals(Integer.toString(theSignature.getColor4Message()), seq.getData());

		seq = list.get(5);
		Assert.assertEquals(Sequence.SEQ_CHORDOWNLOAD, seq.getType());
		Assert.assertEquals(theSignature.getOutroChoregraphyUrl(), seq.getData());
	}

	@Test
	public void fillMessageTest() {
		final VObject theObject = getPrivateObject();
		final MessageDraft theMessage = new MessageDraft(theObject);
		final Anim theAnim = Factories.ANIM.findAllOrderByName().get(0);
		final Music theMusic = new MusicMock(1L, "name", new FilesMock("/home/agata/Desktop/sons/1", MimeType.MIME_TYPES.A_MPEG), null, 7, 0, 0, 0);
		final MessageSignature theSignature = new MessageSignature(5, theAnim, theMusic);
		final Body theBody = new Body(new FilesMock("/home/agata/Desktop/sons/2", MimeType.MIME_TYPES.A_MPEG), Palette.PASTEL, true);
		final Body theOtherBody = new Body(new FilesMock("/home/agata/Desktop/sons/3", MimeType.MIME_TYPES.A_MPEG), Palette.PASTEL, false);
		final Body theOtherMidiBody = new Body(new FilesMock("/home/agata/Desktop/sons/3.mid", MimeType.MIME_TYPES.A_MIDI), Palette.PASTEL, false);

		theMessage.fillMessageDraft("title", theSignature, new Body[] { theBody, theOtherBody, theOtherMidiBody });
		final List<Sequence> list = theMessage.getSequenceList();
		Assert.assertEquals(9, list.size());

		Sequence seq = list.get(0);
		Assert.assertEquals(Sequence.SEQ_MUSICSIGN, seq.getType());
		Assert.assertEquals(theSignature.getIntroMusicUrl(), seq.getData());

		seq = list.get(1);
		Assert.assertEquals(Sequence.SEQ_COLOR, seq.getType());
		Assert.assertEquals(Integer.toString(theSignature.getColor4Message()), seq.getData());

		seq = list.get(2);
		Assert.assertEquals(Sequence.SEQ_CHORDOWNLOAD, seq.getType());
		Assert.assertEquals(theSignature.getIntroChoregraphyUrl(), seq.getData());

		seq = list.get(3);
		Assert.assertEquals(Sequence.SEQ_MUSICSHOULDSTREAM, seq.getType());
		Assert.assertEquals("/home/agata/Desktop/sons/2", seq.getData());

		seq = list.get(4);
		Assert.assertEquals(Sequence.SEQ_MUSICDOWNLOAD, seq.getType());
		Assert.assertEquals("/home/agata/Desktop/sons/3", seq.getData());

		seq = list.get(5);
		Assert.assertEquals(Sequence.SEQ_MUSICDOWNLOAD, seq.getType());
		Assert.assertEquals("/home/agata/Desktop/sons/3.mid", seq.getData());

		seq = list.get(6);
		Assert.assertEquals(Sequence.SEQ_MUSICSIGN, seq.getType());
		Assert.assertEquals(theSignature.getOutroMusicUrl(), seq.getData());

		seq = list.get(7);
		Assert.assertEquals(Sequence.SEQ_COLOR, seq.getType());
		Assert.assertEquals(Integer.toString(theSignature.getColor4Message()), seq.getData());

		seq = list.get(8);
		Assert.assertEquals(Sequence.SEQ_CHORDOWNLOAD, seq.getType());
		Assert.assertEquals(theSignature.getOutroChoregraphyUrl(), seq.getData());
	}

	@Test
	public void addPojoTest() throws InvalidParameterException, ConversionException {
		final String command = "<map><type><string>expression</string></type>" + "<modality><string>net.violet.tts.en</string></modality>" + "<text><string>Hello world!</string></text></map>";

		final MessageDraft theMessage = new MessageDraft(getPrivateObject());
		theMessage.addPojo(command, MimeType.MIME_TYPES.XML);

		final List<SequencePart> list = theMessage.getSequencePart();
		Assert.assertEquals(1, list.size());
		final SequencePart part = list.get(0);
		Assert.assertTrue(part instanceof Expression);
		final Expression expr = (Expression) part;
		final Map<String, Object> pojo = expr.getPojo();
		Assert.assertEquals(3, pojo.size());
		Assert.assertEquals("Hello world!", pojo.get("text"));
		Assert.assertEquals("expression", pojo.get("type"));
		Assert.assertEquals("net.violet.tts.en", pojo.get("modality"));
	}

}
