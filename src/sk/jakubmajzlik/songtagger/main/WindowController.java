package sk.jakubmajzlik.songtagger.main;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class WindowController implements Initializable{
	
	@FXML
	private Label titleLabel;
	@FXML 
	ImageView albumCover;
	@FXML
	private TextField titleField;
	@FXML
	private TextField artistField;
	@FXML
	private TextField albumField;
	@FXML
	private TextField yearField;
	@FXML
	private TextField genreField;
	
	@FXML
	private TextField saveButton;
	
	private AudioFile file;
	private Tag tags;
	
	public void setTags(Tag tags) {
		this.tags = tags;
	}

	public void setFile(AudioFile file) {
		this.file = file;
	}
	
	public ImageView getAlbumCover() {
			return albumCover;
	}
		
	public TextField getAlbumField() {
		return albumField;
	}

	public TextField getYearField() {
		return yearField;
	}

	public TextField getGenreField() {
		return genreField;
	}

	public Label getTitleLabel() {
		return titleLabel;
	}
	
	public TextField getTitleField() {
		return titleField;
	}
	
	public TextField getArtistField() {
		return artistField;
	}
	
	@FXML
	private void changeCover(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		
		//TODO: More filters
		//File type  filters
		FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Portable Network Graphics", "*.png");
		FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("Joint Photographic Experts Group", "*.jpeg");
		FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("Windows Bitmap", "*.bmp");
		fileChooser.getExtensionFilters().add(pngFilter);
		fileChooser.getExtensionFilters().add(jpegFilter);
		fileChooser.getExtensionFilters().add(bmpFilter);
		
		File file = fileChooser.showOpenDialog(new Stage());
		if(file != null) {
			try {
				Image artworkImage = SwingFXUtils.toFXImage(ImageIO.read(file), null);
				albumCover.setImage(artworkImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void saveSongTags(ActionEvent event) {
		try {
			//Getting tags from fields and sevaing to audiofile
			tags.setField(FieldKey.TITLE, titleField.getText());
			tags.setField(FieldKey.ALBUM, albumField.getText());
			tags.setField(FieldKey.ARTIST, artistField.getText());
			tags.setField(FieldKey.YEAR, yearField.getText());
			tags.setField(FieldKey.GENRE, genreField.getText());
			
			//Saving song artwork
			if(albumCover.getImage() != null) {
				File artworkFile = new File("temp_artwork.png");
				ImageIO.write(SwingFXUtils.fromFXImage(albumCover.getImage(), null), "png", artworkFile);
				Artwork artwork = ArtworkFactory.createArtworkFromFile(artworkFile);
				tags.deleteArtworkField();
				tags.setField(artwork);
				artworkFile.delete();
			}
	
			//Commiting changes
			file.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
	}

}
