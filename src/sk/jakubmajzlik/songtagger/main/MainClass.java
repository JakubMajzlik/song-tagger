package sk.jakubmajzlik.songtagger.main;

import java.awt.image.BufferedImage;
import java.io.File;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class MainClass extends Application {

	private String windowFXML = "Window.fxml";
	

	private String filePath;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(windowFXML));
		Parent root = loader.load();
		
		
		
		Scene scene = new Scene(root);
		
		scene.setOnDragOver(event -> {
			Dragboard db = event.getDragboard();
			if(db.hasFiles()) {
				event.acceptTransferModes(TransferMode.COPY);
			} else {
				event.consume();
			}
		});
		
		
		scene.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if(db.hasFiles()) {
				success = true;
				
				for(File file: db.getFiles()) {
					this.filePath = file.getAbsolutePath();
					try {
						AudioFile f = AudioFileIO.read(new File(filePath));
						Tag tags = f.getTag();		
						
						WindowController controller = loader.getController();
						controller.setFile(f);
						controller.setTags(tags);
						controller.getTitleLabel().setText(tags.getFirst(FieldKey.TITLE));
						controller.getTitleField().setText(tags.getFirst(FieldKey.TITLE));
						controller.getAlbumField().setText(tags.getFirst(FieldKey.ALBUM));
						controller.getArtistField().setText(tags.getFirst(FieldKey.ARTIST));
						controller.getGenreField().setText(tags.getFirst(FieldKey.GENRE));
						controller.getYearField().setText(tags.getFirst(FieldKey.YEAR));
						
						Image cover = SwingFXUtils.toFXImage((BufferedImage) tags.getFirstArtwork().getImage(), null);
						controller.getAlbumCover().setImage(cover);
						

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
		
		
		
		
		
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

}