/** @note Copyright (c) 2013 by The Board of Trustees of the Leland Stanford Junior University.
 * All rights reserved.  See {file:LICENSE} for details. **/
package edu.stanford.lucene.analysis;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.BaseTokenStreamFactoryTestCase;

/**
 * Simple tests to ensure the CJKFoldingFilter factory is working.
 * @author Naomi Dushay
 */
public class TestCJKFoldingFilterFactory extends BaseTokenStreamFactoryTestCase
{
	public void testNonCJK() throws Exception
	{
		Reader reader = new StringReader("mahler is the bomb");
		TokenStream stream = tokenizerFactory("standard").create();
		((StandardTokenizer)stream).setReader(reader);
		CJKFoldingFilterFactory factory = new CJKFoldingFilterFactory(new HashMap<String,String>());
		stream = factory.create(stream);
		assertTokenStreamContents(stream, new String[] { "mahler", "is", "the", "bomb" });
	}

	public void testCJKUnfolded() throws Exception
	{
		Reader reader = new StringReader("多の学生が試に落ちた。");
		TokenStream stream = tokenizerFactory("standard").create();
		((StandardTokenizer)stream).setReader(reader);
		CJKFoldingFilterFactory factory = new CJKFoldingFilterFactory(new HashMap<String,String>());
		stream = factory.create(stream);
		assertTokenStreamContents(stream, new String[] { "多", "の", "学", "生", "が", "試", "に", "落", "ち", "た" });
	}

	public void testCJKFolded() throws Exception
	{
		Reader reader = new StringReader("亜亞恶悪惡惡噁応應foo");
		TokenStream stream = tokenizerFactory("standard").create();
		((StandardTokenizer)stream).setReader(reader);
		CJKFoldingFilterFactory factory = new CJKFoldingFilterFactory(new HashMap<String,String>());
		stream = factory.create(stream);
		assertTokenStreamContents(stream, new String[] { "亞", "亞", "噁", "噁", "噁", "噁", "噁", "應", "應", "foo" });
	}
}
