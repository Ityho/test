package com.miduchina.wrd.webthermalquery.controller;


import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.util.PagerUtils;

/**
 * ���Ｏ����struts2��action ��������
 *
 * ���˸�action�еĲ�������
 *
 * @author James
 *
 */
public abstract class GBaseController  {
//public abstract class GBaseController extends ActionSupport {
//	private static final long serialVersionUID = -5692287817871086900L;
//
//	/* ����������� */
//	protected String username;// �û���
//	protected String password;// ����
//	protected String keyword;// �ؼ���
//
//	/* only������� */
//	@SuppressWarnings("rawtypes")
//	protected List list;// �����ҳ���list
//	@SuppressWarnings("rawtypes")
//	protected Map map;// �����ҳ���map
//
//	/* ��ҳ������� */
//	protected int total;// �����ҳ��ļ�¼����
//	protected int maxpage = 0;// �����ҳ������ҳ��
//	protected int pagesize;// ҳ�������һҳ����������
//	protected int page;// ҳ�������ҳ��
//	protected int collectTotal; // �ղ�����
//	protected int cartTotal; // ���ﳵ������
//	protected List<KeyWord> keywordList; // �û��Ĺؼ����б�
//	protected List<KeyWord> validKeywordList; // ��Ч�����б�
//	protected List<KeyWord> noUseKeywordList; // δʹ�÷����б�
//	protected List<KeyWord> overdueKeywordList; // ���ڷ����б�
//	protected List<ProductPackage> packageListAll; // ���в�Ʒ���б�
//	protected List<ProductPackage> packageListFree; // �����Ͳ�Ʒ���б�
//	protected List<ProductPackage> packageListKeyword; // ��ⷽ����Ʒ���б�
//	protected List<ProductPackage> packageListAnalysis; // ȫ���¼����������շѲ�Ʒ���б�
//	protected List<ProductPackage> packageListWeiboAnalysis; // ΢���¼����������շѲ�Ʒ���б�
//	protected List<ProductPackage> packageListBrief; // �����������շѲ�Ʒ���б�
//	protected List<ProductPackage> packageListProductAnalysis; // ��Ʒ���������շѲ�Ʒ���б�
//	protected List<ProductPackage> packageListPro; // רҵ��Ʒ���б�
//	protected List<ProductPackage> packageListSingleWeiboAnalysis; // ����΢��������Ʒ���б�
//	protected List<ProductPackage> packageListExportData; // �������ݲ�Ʒ���б�
//	protected List<ProductPackage> packageListCredit; // ΢���ֲ�Ʒ���б�
//	protected List<ProductPackage> packageListHotReportCount; // �ȶȱ��水������Ʒ��
//	protected List<ProductPackage> packageListHotReportDays; // �ȶȱ��水ʱ����Ʒ��
//	protected List<ProductPackage> packageListComments; // ���۲�Ʒ��
//	protected List<ProductPackage> packageListHotAnalysisDaysNew; // �°��ȶȷ�����Ʒ��
//
//	protected int robotSwitch;//�����˿���
//
//	protected boolean showSharePlanAmountMenu; // �Ƿ���ʾ��������˵�
//
//
//	public int getRobotSwitch() {
//		return robotSwitch;
//	}
//
//	public void setRobotSwitch(int robotSwitch) {
//		this.robotSwitch = robotSwitch;
//	}
//
//	public List<KeyWord> getKeywordList() {
//		return keywordList;
//	}
//
//	public void setKeywordList(List<KeyWord> keywordList) {
//		this.keywordList = keywordList;
//	}
//
//	public int getCollectTotal() {
//		return collectTotal;
//	}
//
//	public void setCollectTotal(int collectTotal) {
//		this.collectTotal = collectTotal;
//	}
//
//	public int getCartTotal() {
//		return cartTotal;
//	}
//
//	public void setCartTotal(int cartTotal) {
//		this.cartTotal = cartTotal;
//	}
//
//	@SuppressWarnings("rawtypes")
//	public Map getMap() {
//		return map;
//	}
//
//	@SuppressWarnings("rawtypes")
//	public List getList() {
//		return list;
//	}
//
//	public int getPagesize() {
//		return pagesize;
//	}
//
//	public void setPagesize(int pagesize) {
//		this.pagesize = pagesize;
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public int getTotal() {
//		return total;
//	}
//
//	public int getMaxpage() {
//		return maxpage;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getKeyword() {
//		return keyword;
//	}
//
//	public void setKeyword(String keyword) {
//		this.keyword = keyword;
//	}
//
//	public List<ProductPackage> getPackageListKeyword() {
//		return packageListKeyword;
//	}
//
//	public void setPackageListKeyword(List<ProductPackage> packageListKeyword) {
//		this.packageListKeyword = packageListKeyword;
//	}
//
//	public List<ProductPackage> getPackageListAnalysis() {
//		return packageListAnalysis;
//	}
//
//	public void setPackageListAnalysis(List<ProductPackage> packageListAnalysis) {
//		this.packageListAnalysis = packageListAnalysis;
//	}
//
//	public List<ProductPackage> getPackageListWeiboAnalysis() {
//		return packageListWeiboAnalysis;
//	}
//
//	public void setPackageListWeiboAnalysis(List<ProductPackage> packageListWeiboAnalysis) {
//		this.packageListWeiboAnalysis = packageListWeiboAnalysis;
//	}
//
//	public List<ProductPackage> getPackageListBrief() {
//		return packageListBrief;
//	}
//
//	public void setPackageListBrief(List<ProductPackage> packageListBrief) {
//		this.packageListBrief = packageListBrief;
//	}
//
//	public List<ProductPackage> getPackageListProductAnalysis() {
//		return packageListProductAnalysis;
//	}
//
//	public void setPackageListProductAnalysis(List<ProductPackage> packageListProductAnalysis) {
//		this.packageListProductAnalysis = packageListProductAnalysis;
//	}
//
//	public List<ProductPackage> getPackageListPro() {
//		return packageListPro;
//	}
//
//	public void setPackageListPro(List<ProductPackage> packageListPro) {
//		this.packageListPro = packageListPro;
//	}
//
//	public List<ProductPackage> getPackageListExportData() {
//		return packageListExportData;
//	}
//
//	public void setPackageListExportData(List<ProductPackage> packageListExportData) {
//		this.packageListExportData = packageListExportData;
//	}
//
//	public List<ProductPackage> getPackageListCredit() {
//		return packageListCredit;
//	}
//
//	public void setPackageListCredit(List<ProductPackage> packageListCredit) {
//		this.packageListCredit = packageListCredit;
//	}
//
//	public List<ProductPackage> getPackageListFree() {
//		return packageListFree;
//	}
//
//	public void setPackageListFree(List<ProductPackage> packageListFree) {
//		this.packageListFree = packageListFree;
//	}
//
//	public List<ProductPackage> getPackageListSingleWeiboAnalysis() {
//		return packageListSingleWeiboAnalysis;
//	}
//
//	public void setPackageListSingleWeiboAnalysis(List<ProductPackage> packageListSingleWeiboAnalysis) {
//		this.packageListSingleWeiboAnalysis = packageListSingleWeiboAnalysis;
//	}
//
//	public List<ProductPackage> getPackageListAll() {
//		return packageListAll;
//	}
//
//	public void setPackageListAll(List<ProductPackage> packageListAll) {
//		this.packageListAll = packageListAll;
//	}
//
//	public List<ProductPackage> getPackageListHotReportCount() {
//		return packageListHotReportCount;
//	}
//
//	public void setPackageListHotReportCount(List<ProductPackage> packageListHotReportCount) {
//		this.packageListHotReportCount = packageListHotReportCount;
//	}
//
//	public List<ProductPackage> getPackageListHotReportDays() {
//		return packageListHotReportDays;
//	}
//
//	public void setPackageListHotReportDays(List<ProductPackage> packageListHotReportDays) {
//		this.packageListHotReportDays = packageListHotReportDays;
//	}
//
//	public List<ProductPackage> getPackageListComments() {
//		return packageListComments;
//	}
//
//	public void setPackageListComments(List<ProductPackage> packageListComments) {
//		this.packageListComments = packageListComments;
//	}
//
//	public boolean isShowSharePlanAmountMenu() {
//		return showSharePlanAmountMenu;
//	}
//
//	public void setShowSharePlanAmountMenu(boolean showSharePlanAmountMenu) {
//		this.showSharePlanAmountMenu = showSharePlanAmountMenu;
//	}
//
	/**
	 * 重置每页显示数量
	 */
	protected void resetPagesize(Integer pagesize,Integer page) {
		if (pagesize <= 0) {
			pagesize = PagerUtils.PAGE_SIZE*5;
		}
		if (page <= 0) {
			page = 1;
		}
	}
//	// ��ȡ���������ֵ ����2λ
//
	protected abstract UserDto fetchSessionAdmin();
//
//	protected abstract void fetchDwrSessionAdmin();
//
//	public List<ProductPackage> getPackageListHotAnalysisDaysNew() {
//		return packageListHotAnalysisDaysNew;
//	}
//
//	public void setPackageListHotAnalysisDaysNew(
//			List<ProductPackage> packageListHotAnalysisDaysNew) {
//		this.packageListHotAnalysisDaysNew = packageListHotAnalysisDaysNew;
//	}
//
//	public List<KeyWord> getValidKeywordList() {
//		return validKeywordList;
//	}
//
//	public void setValidKeywordList(List<KeyWord> validKeywordList) {
//		this.validKeywordList = validKeywordList;
//	}
//
//	public List<KeyWord> getNoUseKeywordList() {
//		return noUseKeywordList;
//	}
//
//	public void setNoUseKeywordList(List<KeyWord> noUseKeywordList) {
//		this.noUseKeywordList = noUseKeywordList;
//	}
//
//	public List<KeyWord> getOverdueKeywordList() {
//		return overdueKeywordList;
//	}
//
//	public void setOverdueKeywordList(List<KeyWord> overdueKeywordList) {
//		this.overdueKeywordList = overdueKeywordList;
//	}
}
