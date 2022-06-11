package custom.person.database;

public class CustomPersonLoginVO {
	private String id;
	private String shaPwd;
	private int customId;
	private String customName;
	private char customGrade;
	private String gradeName;
	private int point;
	private int visitCnt;
	private int todayCnt;
	private String loginDate;
	private String logoutDate;
	private String createDate;
	private String updateDate;
	private String deleteDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShaPwd() {
		return shaPwd;
	}
	public void setShaPwd(String shaPwd) {
		this.shaPwd = shaPwd;
	}
	public int getCustomId() {
		return customId;
	}
	public void setCustomId(int customId) {
		this.customId = customId;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public char getCustomGrade() {
		return customGrade;
	}
	public void setCustomGrade(char customGrade) {
		this.customGrade = customGrade;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getVisitCnt() {
		return visitCnt;
	}
	public void setVisitCnt(int visitCnt) {
		this.visitCnt = visitCnt;
	}
	public int getTodayCnt() {
		return todayCnt;
	}
	public void setTodayCnt(int todayCnt) {
		this.todayCnt = todayCnt;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLogoutDate() {
		return logoutDate;
	}
	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	@Override
	public String toString() {
		return "CustomPersonLoginVO [id=" + id + ", shaPwd=" + shaPwd + ", customId=" + customId + ", customName="
				+ customName + ", customGrade=" + customGrade + ", gradeName=" + gradeName + ", point=" + point + ", visitCnt="
				+ visitCnt + ", todayCnt=" + todayCnt + ", loginDate=" + loginDate + ", logoutDate=" + logoutDate
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + ", deleteDate=" + deleteDate + "]";
	}

}