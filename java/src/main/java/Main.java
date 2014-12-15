
import java.awt.EventQueue;

import FIIT.VI.YAGO.ui.MainController;
import FIIT.VI.YAGO.ui.MainModel;
import FIIT.VI.YAGO.ui.MainUI;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainController(new MainModel(), new MainUI());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
