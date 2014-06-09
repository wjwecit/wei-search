/**
 * 
 */
package wei.test.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import wei.db.common.DbTemplate;
import wei.db.common.MysqlPageTable;
import wei.db.common.TransactionExecutor;
import wei.web.mvc.model.AreaChina;
import wei.web.util.LuceneUtil;

/**
 * @author wei
 * 
 */
public class Dbtest {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		String str="现在要把baidu着色，也就是要把baidu替换成<font style='color:red'>baidu</a>因为html标签如a,img等里面的内容是不能替换的，所以这就要用到PHP的正则环视结构了。分析：正文baidu中，左边是不存在但存在<...>，同理，右边不存在但存在<....>于是，解决方案如下事实上“定向降准”<a 并非央行新采用的货币政策工>具</a>。几年前部分县域法人金融机构就已获准可定向降准，即执行低于同类金融机构１个百分点上缴准备金率的政策优惠。央行与银监会曾制定“鼓励县域法人金融机构将新增存款一定比例用于当地贷款”的考核办法，根据这一考核办法，对于达标的县域法人金融机构，存款准备金率按低于同类金融机构正常标准１个百分点执行，这一考核办法凸显出央行“定向降准”的正向激励原则。";
		
		String reg="(?!<[^>]*)((现在)|(要把)|(baidu)|(着色)|(也就是)|(要把)|(baidu)|(替换)|(成)|(font)|(style)|(color)|(red)|(baidu)|(a)|(因为)|(html)|(标签)|(如)|(a)|(img)|(等)|(里面)|(的)|(内容)|(是)|(不能)|(替换)|(的)|(所以)|(这就)|(要用)|(到)|(php)|(的)|(正)|(则)|(环视)|(结构)|(了)|(分析)|(正文)|(baidu)|(中)|(左边)|(是)|(不存在)|(但)|(存在)|(同理)|(右边)|(不存在)|(但)|(存在)|(于是)|(解决)|(方案)|(如下)|(事实上)|(定向)|(降)|(准)|(a)|(并非)|(央行)|(新)|(采用)|(的)|(货币政策)|(工)|(具)|(a)|(几年)|(前)|(部分)|(县)|(域)|(法人)|(金融机构)|(就)|(已获)|(准)|(可)|(定向)|(降)|(准)|(即)|(执行)|(低于)|(同类)|(金融机构)|(1)|(个)|(百分点)|(上缴)|(准备金)|(率)|(的)|(政策)|(优惠)|(央行)|(与)|(银)|(监)|(会)|(曾)|(制定)|(鼓励)|(县)|(域)|(法人)|(金融机构)|(将)|(新增)|(存款)|(一定)|(比例)|(用于)|(当地)|(贷款)|(的)|(考核)|(办法)|(根据)|(这一)|(考核)|(办法)|(对于)|(达标)|(的)|(县)|(域)|(法人)|(金融机构)|(存款)|(准备金)|(率)|(按)|(低于)|(同类)|(金融机构)|(正常)|(标准)|(1)|(个)|(百分点)|(执行)|(这一)|(考核)|(办法)|(凸)|(显出)|(央行)|(定向)|(降)|(准)|(的)|(正向)|(激励)|(原则)|(b))(?![^<]*>)";
		for(int i=0;i<10;i++){
			long start=System.currentTimeMillis();
			System.out.println("reg="+reg+"\n"+str.replaceAll(reg, "#"));
			System.out.println("total time:"+(System.currentTimeMillis()-start));
		}
		
//		try {
//			for(int i=0;i<10;i++){
//				long start=System.currentTimeMillis();
//				LuceneUtil.analyze(LuceneUtil.analyzer, str);
//				System.out.println("total time:"+(System.currentTimeMillis()-start));
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static void main2(String[] args) {

		String str = "<script type=\"text/javascript\"></script><script type=\"text/javascript\" src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\"></script>";
		str = str.replace("\"", "\\\"");
		System.out.println(str);
		MysqlPageTable table = new MysqlPageTable();
		table.setSql("select * from areachina");
		ArrayList<HashMap<String, String>> list = table.getDataArray();
		for (HashMap<String, String> map : list) {
			System.out.println(map.get("areaCode"));
		}

	}
	/*
	 * static void testdb(){
	 * final DbTemplate temp=new DbTemplate();
	 * try {
	 * temp.doWithinTransaction(new TransactionExecutor() {
	 * 
	 * @Override
	 * public void execute() throws SQLException {
	 * AreaChina bean=new AreaChina();
	 * bean.setAreaCode(755);
	 * bean.setAreaName("鹏城深圳aaaaaaa");
	 * bean.setAreaCodeDeprecated(new Random().nextInt(800800)+"");
	 * temp.update(bean);
	 * bean=temp.getBean(AreaChina.class, "select * from areachina where areaCode=755");
	 * System.out.println(bean.toString());
	 * }
	 * 
	 * });
	 * 
	 * System.out.println("endS");
	 * } catch (Exception e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 */

}
