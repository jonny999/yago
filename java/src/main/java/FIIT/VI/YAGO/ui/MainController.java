package FIIT.VI.YAGO.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;

/**
 * Main window controller
 * @author mm
 *
 */
public class MainController {

	private MainUI ui;
	private MainModel model;

	public MainController(MainModel model, MainUI mainUi) {
		this.model = model;
		this.ui = mainUi;
		initilialize();
	}

	/**
	 * UI setup
	 */
	private void initilialize() {
		ui.getFrame().setVisible(true);
		ui.addSearchLister(new SearchListener());
	}

	/**
	 * Action listener for search
	 * @author mm
	 *
	 */
	private class SearchListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String search = ui.getSearch();
			if (!"".equals(search)) {
				ui.setTextArea("\n");
				final long startTime = System.currentTimeMillis();
				List<Document> docs = model.searchIndex(search);
				final long time = System.currentTimeMillis()-startTime;
				
				ui.appendArea("Search for "+search+" in time "+time+"\n");
				ui.appendArea("=========================\n");
				
				for (Document doc : docs) {
					ui.appendArea(doc.get("name") +this.getDocument(doc) + "\n");
				}
			}
		}
		
		private List<String> getDocument(Document document){
			List<String> docs = new ArrayList<String>();
			docs.add(document.get("name")==null?"":document.get("name").replaceAll("_", " "));
			docs.add(document.get("ulrWikipedia")==null?"":document.get("ulrWikipedia"));
			
			return docs;
		}
	}
}
