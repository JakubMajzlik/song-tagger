package sk.jakubmajzlik.songtagger.main;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


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
	private void saveSongTags(ActionEvent event) {
		try {
			tags.setField(FieldKey.TITLE, titleField.getText());
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
