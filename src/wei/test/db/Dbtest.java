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

		String str="����Ҫ��baidu��ɫ��Ҳ����Ҫ��baidu�滻��<font style='color:red'>baidu</a>��Ϊhtml��ǩ��a,img������������ǲ����滻�ģ��������Ҫ�õ�PHP�������ӽṹ�ˡ�����������baidu�У�����ǲ����ڵ�����<...>��ͬ���ұ߲����ڵ�����<....>���ǣ��������������ʵ�ϡ�����׼��<a ���������²��õĻ������߹�>��</a>������ǰ���������˽��ڻ������ѻ�׼�ɶ���׼����ִ�е���ͬ����ڻ��������ٷֵ��Ͻ�׼�����ʵ������Żݡ���������������ƶ������������˽��ڻ������������һ���������ڵ��ش���Ŀ��˰취��������һ���˰취�����ڴ��������˽��ڻ��������׼�����ʰ�����ͬ����ڻ���������׼�����ٷֵ�ִ�У���һ���˰취͹�Գ����С�����׼����������ԭ��";
		
		String reg="(?!<[^>]*)((����)|(Ҫ��)|(baidu)|(��ɫ)|(Ҳ����)|(Ҫ��)|(baidu)|(�滻)|(��)|(font)|(style)|(color)|(red)|(baidu)|(a)|(��Ϊ)|(html)|(��ǩ)|(��)|(a)|(img)|(��)|(����)|(��)|(����)|(��)|(����)|(�滻)|(��)|(����)|(���)|(Ҫ��)|(��)|(php)|(��)|(��)|(��)|(����)|(�ṹ)|(��)|(����)|(����)|(baidu)|(��)|(���)|(��)|(������)|(��)|(����)|(ͬ��)|(�ұ�)|(������)|(��)|(����)|(����)|(���)|(����)|(����)|(��ʵ��)|(����)|(��)|(׼)|(a)|(����)|(����)|(��)|(����)|(��)|(��������)|(��)|(��)|(a)|(����)|(ǰ)|(����)|(��)|(��)|(����)|(���ڻ���)|(��)|(�ѻ�)|(׼)|(��)|(����)|(��)|(׼)|(��)|(ִ��)|(����)|(ͬ��)|(���ڻ���)|(1)|(��)|(�ٷֵ�)|(�Ͻ�)|(׼����)|(��)|(��)|(����)|(�Ż�)|(����)|(��)|(��)|(��)|(��)|(��)|(�ƶ�)|(����)|(��)|(��)|(����)|(���ڻ���)|(��)|(����)|(���)|(һ��)|(����)|(����)|(����)|(����)|(��)|(����)|(�취)|(����)|(��һ)|(����)|(�취)|(����)|(���)|(��)|(��)|(��)|(����)|(���ڻ���)|(���)|(׼����)|(��)|(��)|(����)|(ͬ��)|(���ڻ���)|(����)|(��׼)|(1)|(��)|(�ٷֵ�)|(ִ��)|(��һ)|(����)|(�취)|(͹)|(�Գ�)|(����)|(����)|(��)|(׼)|(��)|(����)|(����)|(ԭ��)|(b))(?![^<]*>)";
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
	 * bean.setAreaName("��������aaaaaaa");
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
