package custom.kind.database;

public class CustomKindVO {
	private int customKindCd;
	private String customKindNm;
	public int getCustomKindCd() {
		return customKindCd;
	}
	public void setCustomKindCd(int customKindCd) {
		this.customKindCd = customKindCd;
	}
	public String getCustomKindNm() {
		return customKindNm;
	}
	public void setCustomKindNm(String customKindNm) {
		this.customKindNm = customKindNm;
	}
	@Override
	public String toString() {
		return "CustomKindVO [customKindCd=" + customKindCd + ", customKindNm=" + customKindNm + "]";
	}
}