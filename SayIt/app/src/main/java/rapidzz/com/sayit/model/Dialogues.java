package rapidzz.com.sayit.model;

/**
 * Created by rapidzz on 26-Mar-16.
 */

public class Dialogues {

    private int id;
    private String mDialogue;
    private String mLink;
    private String mCatTitle;
    public Dialogues(int id, String dialogue, String link,String  catTitle) {
        super();

        this.id = id;
        mDialogue = dialogue;
        mLink = link;
        mCatTitle = catTitle;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDialogue() {
        return mDialogue;
    }
    public void setDialogue(String dialogue) {
        mDialogue = dialogue;
    }
    public String getLink() {
        return mLink;
    }
    public void setLink(String link) {
        mLink = link;
    }
    public String getmCatTitle() {
        return mCatTitle;
    }

    public void setmCatTitle(String mCatTitle) {
        this.mCatTitle = mCatTitle;
    }

    @Override
    public String toString() {
        return "Dialogues [id=" + id + ", mDialogue=" + mDialogue + ", mLink="
                + mLink + "]";
    }





}
