package rapidzz.product.sayinstyle.model;

public class Favorities {

	private int mId;
	private String mfvrt;
	private String mLink;
	private String mCatTitle;


	public Favorities(int mId,String mfvrt,String catTitle,String mLink) {
		super();
		this.mfvrt = mfvrt;
		this.mId = mId;
		this.mCatTitle = catTitle;
		this.mLink = mLink;
	}




	public String getmCatTitle() {
		return mCatTitle;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmLink() {
		return mLink;
	}

	public void setmLink(String mLink) {
		this.mLink = mLink;
	}

	public void setmCatTitle(String mCatTitle) {
		this.mCatTitle = getmCatTitle();
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getMfvrt() {
		return mfvrt;
	}
	public void setMfvrt(String mfvrt) {
		this.mfvrt = mfvrt;
	}

	public String getLink() {
		return mLink;
	}

	public void setLink(String link) {
		mLink = link;
	}

	@Override
	public String toString() {
		return "mId=" + mId + ", mfvrt=" + mfvrt + ", mLink="
				+ mLink+"\n";
	}
	
	
}
