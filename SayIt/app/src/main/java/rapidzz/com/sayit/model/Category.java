package rapidzz.com.sayit.model;

/**
 * Created by rapidzz on 05-Mar-16.
 */
public class Category {


    //private String imgPath;
    int id;
    String catTitle;
    String catLogo;
    String catBackImg;
    String catCountHindi;
    String  catCountEnglish;
    String  catUpcoming;
    String  catStatus;

    public Category(int id,String catBackImg, String catCountEnglish, String catCountHindi, String catLogo, String catStatus, String catTitle, String catUpcoming ) {
        this.catBackImg = catBackImg;
        this.catCountEnglish = catCountEnglish;
        this.catCountHindi = catCountHindi;
        this.catLogo = catLogo;
        this.catStatus = catStatus;
        this.catTitle = catTitle;
        this.catUpcoming = catUpcoming;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatUpcoming() {
        return catUpcoming;
    }

    public void setCatUpcoming(String catUpcoming) {
        this.catUpcoming = catUpcoming;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCatStatus() {
        return catStatus;
    }

    public void setCatStatus(String catStatus) {
        this.catStatus = catStatus;
    }

    public String getCatLogo() {
        return catLogo;
    }

    public void setCatLogo(String catLogo) {
        this.catLogo = catLogo;
    }

    public String getCatCountHindi() {
        return catCountHindi;
    }

    public void setCatCountHindi(String catCountHindi) {
        this.catCountHindi = catCountHindi;
    }

    public String getCatCountEnglish() {
        return catCountEnglish;
    }

    public void setCatCountEnglish(String catCountEnglish) {
        this.catCountEnglish = catCountEnglish;
    }

    public String getCatBackImg() {
        return catBackImg;
    }

    public void setCatBackImg(String catBackImg) {
        this.catBackImg = catBackImg;
    }
}
