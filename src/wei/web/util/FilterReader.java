package wei.web.util;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FilterAtomicReader;
import org.apache.lucene.index.FilterDirectoryReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.OpenBitSet;

public class FilterReader extends FilterAtomicReader {

	OpenBitSet dels;
	public FilterReader(AtomicReader in) {
		super(in);
		// TODO Auto-generated constructor stub
	}



	@Override
	public Bits getLiveDocs() {
		// TODO Auto-generated method stub
		return super.getLiveDocs();
	}



}
