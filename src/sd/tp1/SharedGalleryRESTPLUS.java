package sd.tp1;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import sd.tp1.gui.impl.GalleryWindow;

	/*
	 * Launches the local shared gallery application.
	 */
	public class SharedGalleryRESTPLUS extends Application {

		GalleryWindow window;

		public SharedGalleryRESTPLUS() throws IOException {
			window = new GalleryWindow( new SharedGalleryContentProviderRESTPLUS());
		}	


		public static void main(String[] args){
			launch(args);
		}

		@Override
		public void start(Stage primaryStage) throws Exception {
			window.start(primaryStage);
		}
}
