package net.violet.platform.util;

import java.io.CharArrayWriter;
import java.io.IOException;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class SafeBase64Test extends MockTestBase {

	private final String[] quote = { "Man is distinguished, not only by his reason, but by this singular passion from other animals, which is a lust of the mind, that by a perseverance of delight in the continued and indefatigable generation of knowledge, exceeds the short vehemence of any carnal pleasure.", "日本著名的温泉白滨温泉自飞鸟、奈良时代（6世纪下半叶～8世纪）起，就以“牟娄温汤”、“纪温汤”之名广为人知，齐明、天智、持统、文武天皇等都曾来此，迄今已有 1300余年的历史。三面临太平洋的白滨气候温暖，沿海分布着大自然鬼斧神工的千叠敷、三段壁、圆月岛等风景名胜。温泉浴场与白良滨海水浴场相邻，游客可以穿着泳衣直接入浴。", "Hello SpaceBoy ! Transmission test : 0123456789", };

	private final String[] encodedQuote = { "TWFuIGlzIGRpc3Rpbmd1aXNoZWQsIG5vdCBvbmx5IGJ5IGhpcyByZWFzb24sIGJ1dCBieSB0aGlzIHNpbmd1bGFyIHBhc3Npb24gZnJvbSBvdGhlciBhbmltYWxzLCB3aGljaCBpcyBhIGx1c3Qgb2YgdGhlIG1pbmQsIHRoYXQgYnkgYSBwZXJzZXZlcmFuY2Ugb2YgZGVsaWdodCBpbiB0aGUgY29udGludWVkIGFuZCBpbmRlZmF0aWdhYmxlIGdlbmVyYXRpb24gb2Yga25vd2xlZGdlLCBleGNlZWRzIHRoZSBzaG9ydCB2ZWhlbWVuY2Ugb2YgYW55IGNhcm5hbCBwbGVhc3VyZS4=",
			"5pel5pys6JGX5ZCN55qE5rip5rOJ55m95ruo5rip5rOJ6Ieq6aOe6bif44CB5aWI6Imv5pe25Luj77yINuS4lue6quS4i.WNiuWPtu.9njjkuJbnuqrvvInotbfvvIzlsLHku6XigJzniZ_lqITmuKnmsaTigJ3jgIHigJznuqrmuKnmsaTigJ3kuYvlkI3lub_kuLrkurrnn6XvvIzpvZDmmI7jgIHlpKnmmbrjgIHmjIHnu5_jgIHmlofmrablpKnnmofnrYnpg73mm77mnaXmraTvvIzov4Tku4rlt7LmnIkgMTMwMOS9meW5tOeahOWOhuWPsuOAguS4iemdouS4tOWkquW5s.a0i.eahOeZvea7qOawlOWAmea4qeaalu.8jOayv.a1t.WIhuW4g.edgOWkp.iHqueEtumsvOaWp.elnuW3peeahOWNg.WPoOaVt.OAgeS4ieauteWjgeOAgeWchuaciOWym.etiemjjuaZr.WQjeiDnOOAgua4qeaziea1tOWcuuS4jueZveiJr.a7qOa1t.awtOa1tOWcuuebuOmCu..8jOa4uOWuouWPr.S7peepv.edgOazs.iho.ebtOaOpeWFpea1tOOAgg==", "SGVsbG8gU3BhY2VCb3kgISBUcmFuc21pc3Npb24gdGVzdCA6IDAxMjM0NTY3ODk=", };

	@Test
	public void testEncodeString() {
		final int len = this.quote.length;
		for (int i = 0; i < len; i++) {
			final String result = SafeBase64.encodeString(this.quote[i], true);
			System.out.println(result);
			Assert.assertEquals(result, this.encodedQuote[i]);
		}
	}

	@Test
	public void testEncodeByteArray() {
		final int len = this.quote.length;
		for (int i = 0; i < len; i++) {
			final String str64 = SafeBase64.encode(this.quote[i].getBytes(), true);
			System.out.println(str64);
			Assert.assertEquals(str64, this.encodedQuote[i]);
		}

		final byte[] enumAll = new byte[256];
		for (int i = 0; i < 256; i++) {
			enumAll[i] = (byte) i;
		}
		final String str64 = SafeBase64.encode(enumAll, true);
		// 0 to 255 bytes to Base64
		System.out.println(str64);

		final byte[] backToBytes = SafeBase64.decode(str64, true);
		for (int i = 0; i < 256; i++) {
			Assert.assertEquals(enumAll[i], backToBytes[i]);
		}
	}

	// @Test TODO FIXME test
	public void testEncodeByteArrayCharArrayWriter() throws IOException {
		final int len = this.quote.length;
		for (int i = 0; i < len; i++) {
			final CharArrayWriter writer = new CharArrayWriter();
			writer.write(">>>".toCharArray());
			SafeBase64.encodeTo(this.quote[i].getBytes(), true, writer);
			writer.write("<<<".toCharArray());

			final String result = writer.toString();
			System.out.println(result);

			Assert.assertEquals(result, ">>>" + this.encodedQuote[i] + "<<<");
		}
	}

	@Test
	public void testDecodeString() {
		final int len = this.quote.length;
		for (int i = 0; i < len; i++) {
			final String result = SafeBase64.decodeString(this.encodedQuote[i], true);
			System.out.println(result);
			Assert.assertEquals(result, this.quote[i]);
		}
	}

	@Test
	public void testDecodeCharArray() {
		final int len = this.quote.length;
		for (int i = 0; i < len; i++) {
			final String result = new String(SafeBase64.decode(this.encodedQuote[i].toCharArray(), true));
			System.out.println(result);
			Assert.assertEquals(result, this.quote[i]);
		}
	}
}
