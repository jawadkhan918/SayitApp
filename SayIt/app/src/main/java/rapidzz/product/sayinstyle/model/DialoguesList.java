package rapidzz.product.sayinstyle.model;

import java.util.ArrayList;

import java.util.ArrayList;

public class DialoguesList {

	private ArrayList<Dialogues> mDialogues;
	private static DialoguesList sDialoguesList;

	private DialoguesList() {
		mDialogues = new ArrayList<Dialogues>();
	}
	public static DialoguesList get() {
		if (sDialoguesList == null) {
			sDialoguesList = new DialoguesList();
		}
		return sDialoguesList;
	}
	public ArrayList<Dialogues> getDialogues() {
		return mDialogues;
	}

	public Boolean getDial(String cat) {

		for (Dialogues m : mDialogues) {
			if (m.getDialogue().equals(cat))
				return true;
		}
		return false;
	}

	public String getDialogueById(int cat) {

		for (Dialogues m : mDialogues) {
			if (m.getId()==cat)
				return m.getLink();
		}
		return "";
	}



	public void addDialogue(Dialogues newCat) {
		mDialogues.add(newCat);
	}

	public void deleteMember(Dialogues m) {
		mDialogues.remove(m);

	}
	public void clearDialogues(){

		mDialogues.clear();

	}

}
