/**
 * 
 */
package wei.web.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import wei.db.common.DBConnectionManager;

import com.chenlb.mmseg4j.analysis.SimpleAnalyzer;

/**
 * @author Qin-Wei
 * 
 */
public class LuceneUtil {

	private DBConnectionManager dbm = new DBConnectionManager();

	private static String mainIndexPath = getRootPath() + "/IndexMain";
	private static IndexWriter mainIndexWriter;
	private static IndexWriter ramIndexWriter;
	private static Directory mainDir;

	public static Analyzer analyzer = new SimpleAnalyzer();

	private static IndexReader mainReader = null;
	private static IndexReader ramReader = null;

	private static RAMDirectory ramDir = null;

	private static MultiReader reader;

	private static IndexSearcher searcher;

	static {

		try {
			File indexPath = new File(mainIndexPath);
			if (!indexPath.exists()) {
				indexPath.mkdirs();
			}
			mainDir = MMapDirectory.open(indexPath);
			mainIndexWriter = new IndexWriter(mainDir, getIndexWriterConfig(false));
			mainIndexWriter.commit();
			mainReader = DirectoryReader.open(mainDir);

			ramDir = new RAMDirectory();
			ramIndexWriter = new IndexWriter(ramDir, getIndexWriterConfig(true));
			ramIndexWriter.commit();
			ramReader = DirectoryReader.open(ramDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "现在要把baidu着色，也就是要把baidu替换成<font style='color:red'>baidu</a>因为html标签如a,img等里面的内容是不能替换的，所以这就要用到PHP的正则环视结构了。分析：正文baidu中，左边是不存在但存在<...>，同理，右边不存在但存在\\<....\\>于是，解决方案如下事实上“定向降准”<a 并非央行新采用的货币政策工>具</a>。几年前部分县域法人金融机构就已获准可定向降准，即执行低于同类金融机构１个百分点上缴准备金率的政策优惠。央行与银监会曾制定“鼓励县域法人金融机构将新增存款一定比例用于当地贷款”的考核办法，根据这一考核办法，对于达标的县域法人金融机构，存款准备金率按低于同类金融机构正常标准１个百分点执行，这一考核办法凸显出央行“定向降准”的正向激励原则。";
		str = "关于促进南部地区现在要把baidu着色，也就是要把baidu替换成";
		try {

			for (int i = 0; i < 10; i++) {
				Document doc = new Document();
				IntField f1 = new IntField("id", i, Store.YES);
				StringField f2 = new StringField("name", new Random().nextInt() + "石", Store.YES);
				TextField f3 = new TextField("content", i +":"+ str, Store.YES);
				doc.add(f1);
				doc.add(f2);
				doc.add(f3);
				ramIndexWriter.addDocument(doc);
			}
			ramIndexWriter.commit();
			str = "现在要把baidu关于也就是";
			search(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void index(String path) {
		Connection conn = dbm.getConnection();
		try {
			long start = System.currentTimeMillis();
			Statement st = conn.createStatement();
			st.setFetchSize(Integer.MIN_VALUE);
			ResultSet rs = st.executeQuery("select * from docs where id<100000");

			System.out.println(path);
			File indexDir = new File(path);
			if (!indexDir.exists()) {
				indexDir.mkdirs();
			}
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);

			iwc.setOpenMode(OpenMode.CREATE);
			iwc.setRAMBufferSizeMB(256.0);

			while (rs.next()) {
				Document doc = new Document();
				IntField f1 = new IntField("id", Integer.parseInt(rs.getObject("id").toString()), Store.YES);
				StringField f2 = new StringField("name", rs.getObject("name").toString(), Store.YES);
				TextField f3 = new TextField("content", rs.getObject("content").toString(), Store.YES);
				doc.add(f1);
				doc.add(f2);
				doc.add(f3);
				ramIndexWriter.addDocument(doc);
			}
			ramIndexWriter.commit();
			System.out.println(System.currentTimeMillis() - start);
			ramReader = DirectoryReader.open(ramDir);
			reader = new MultiReader(ramReader, mainReader);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void search(String querystr) {
		try {
			ramReader = DirectoryReader.open(ramDir);
			searcher = new IndexSearcher(ramReader);
			long start = System.currentTimeMillis();
			BooleanQuery bquery = new BooleanQuery();
			MultiPhraseQuery mquery = new MultiPhraseQuery();
			mquery.setSlop(10);
			StringBuilder regBuilder = new StringBuilder("(?!<[^>]*)(");
			for (String term : getFenci(querystr)) {
				bquery.add(new TermQuery(new Term("content", term)), Occur.MUST);
				mquery.add(new Term("content", term));
				regBuilder.append("(?:" + term + ")|");
			}
			regBuilder.subSequence(0, regBuilder.length() - 1);
			regBuilder.replace(regBuilder.length() - 1, regBuilder.length(), ")(?![^<]*>)");

			TopDocs results = searcher.search(mquery, 5, Sort.RELEVANCE);
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			System.out.println("hit:"+numTotalHits+" time:" + (System.currentTimeMillis() - start));
			int i = 0;
			for (ScoreDoc d : hits) {
				Document doc = searcher.doc(d.doc);
				System.out.println(i++ + ":docID:" + d.doc + "id:" + doc.get("id"));
				System.out.println("content:" + doc.get("content").replaceAll(regBuilder.toString(), "<red>$1</red>"));
			}
			System.out.println("search time:" + (System.currentTimeMillis() - start));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addDoc(Document doc) {
		try {
			ramIndexWriter.addDocument(doc);
			ramIndexWriter.commit();

			ramReader = DirectoryReader.open(ramDir);
			reader = new MultiReader(mainReader, ramReader);
			searcher = new IndexSearcher(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addDoc() {
		Document doc = new Document();
		IntField f1 = new IntField("id", new Random().nextInt(), Store.YES);
		StringField f2 = new StringField("name", new Random().nextInt() + "石", Store.YES);
		TextField f3 = new TextField("content", new Random().nextInt() + "石", Store.YES);
		doc.add(f1);
		doc.add(f2);
		doc.add(f3);
		addDoc(doc);
	}

	private static IndexWriterConfig getIndexWriterConfig(boolean replaceOld) {
		try {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, analyzer);
			config.setMaxBufferedDocs(5000);
			config.setOpenMode(replaceOld ? OpenMode.CREATE : OpenMode.CREATE_OR_APPEND);
			config.setRAMBufferSizeMB(256.0);
			config.setUseCompoundFile(false);
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static ArrayList<String> getFenci(String text) {
		ArrayList<String> res = new ArrayList<String>();
		try {
			TokenStream tokenStrm = analyzer.tokenStream("content", new StringReader(text));
			tokenStrm.reset();
			OffsetAttribute offsetAttr = tokenStrm.getAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttr = tokenStrm.getAttribute(CharTermAttribute.class);
			PositionIncrementAttribute posIncrAttr = tokenStrm.addAttribute(PositionIncrementAttribute.class);
			TypeAttribute typeAttr = tokenStrm.addAttribute(TypeAttribute.class);
			String term = null;
			char[] charBuf = null;
			int termPos = 0;
			int termIncr = 0;
			while (tokenStrm.incrementToken()) {
				charBuf = charTermAttr.buffer();
				termIncr = posIncrAttr.getPositionIncrement();
				term = new String(charBuf, 0, offsetAttr.endOffset() - offsetAttr.startOffset());
				if (!res.contains(term)) {
					res.add(term);
				}
				System.out.print("[" + term + ":" + termPos + "/" + termIncr + ":" + typeAttr.type() + ";"
						+ offsetAttr.startOffset() + "-" + offsetAttr.endOffset() + "]  ");
			}
			tokenStrm.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static String getRootPath() {
		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String rootPath = "";
		// windows下
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		return rootPath;
	}

	public static void add(int id) {
		Document doc = new Document();
		doc.add(new IntField("id", id, Store.YES));
		doc.add(new TextField("content", String.valueOf(id) + ":content", Store.YES));
		try {
			ramIndexWriter.addDocument(doc);
			ramIndexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void del(int id) {
		try {
			ramIndexWriter.deleteDocuments(new Term("id", String.valueOf(id)));
			ramIndexWriter.commit();
			System.out.println(ramReader.maxDoc() + " docs left!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
