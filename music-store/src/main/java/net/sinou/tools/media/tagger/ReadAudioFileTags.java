package net.sinou.tools.media.tagger;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class ReadAudioFileTags {

	public void readWithJaudioTagger(File audioFile) {
		AudioFile f;
		try {
			System.out.println("=== Getting info for " + audioFile.getName() + " using JAudio Tagger");

			f = AudioFileIO.read(audioFile);
			f.getAudioHeader();
			// System.out.println(f.displayStructureAsPlainText());
			// System.out.println(f.displayStructureAsXML());;
			Tag tag = f.getTag();
			Iterator<TagField> it = tag.getFields();
			while (it.hasNext()) {
				TagField field = it.next();
				System.out.println(field.getId() + ": " + field.toString());
				System.out.println(field.getId() + ": " + field.toString());
			}

			// AudioHeader
			// eclipse-javadoc:%E2%98%82=playground/%5C/home%5C/bsinou%5C/.m2%5C/repository%5C/net%5C/jthink%5C/jaudiotagger%5C/2.2.5%5C/jaudiotagger-2.2.5.jar%3Corgheader
			// = f.getAudioHeader();
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * See https://github.com/mpatric/mp3agic
	 * 
	 * @param audioFile
	 */
	public void readWithMp3Magic(File audioFile) {
		try {
			System.out.println("=== Getting info for " + audioFile.getName() + " using Mp3 Magic");
			Mp3File mp3file = new Mp3File(audioFile);
			if (mp3file.hasId3v1Tag())
				readId3v1(mp3file);
			if (mp3file.hasId3v2Tag())
				readId3v2(mp3file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Getting ID3v1 values
	private void readId3v1(Mp3File mp3file) {
		ID3v1 id3v1Tag = mp3file.getId3v1Tag();
		System.out.println("Track: " + id3v1Tag.getTrack());
		System.out.println("Artist: " + id3v1Tag.getArtist());
		System.out.println("Title: " + id3v1Tag.getTitle());
		System.out.println("Album: " + id3v1Tag.getAlbum());
		System.out.println("Year: " + id3v1Tag.getYear());
		System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
		System.out.println("Comment: " + id3v1Tag.getComment());
	}

	// Getting ID3v2 frame values
	private void readId3v2(Mp3File mp3file) {
		ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		System.out.println("Track: " + id3v2Tag.getTrack());
		System.out.println("Artist: " + id3v2Tag.getArtist());
		System.out.println("Title: " + id3v2Tag.getTitle());
		System.out.println("Album: " + id3v2Tag.getAlbum());
		System.out.println("Year: " + id3v2Tag.getYear());
		System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
		System.out.println("Comment: " + id3v2Tag.getComment());
		System.out.println("Lyrics: " + id3v2Tag.getLyrics());
		System.out.println("Composer: " + id3v2Tag.getComposer());
		System.out.println("Publisher: " + id3v2Tag.getPublisher());
		System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
		System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
		System.out.println("Copyright: " + id3v2Tag.getCopyright());
		System.out.println("URL: " + id3v2Tag.getUrl());
		System.out.println("Encoder: " + id3v2Tag.getEncoder());
		byte[] albumImageData = id3v2Tag.getAlbumImage();
		if (albumImageData != null) {
			System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
			System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
		}
	}

	public void normaliseMp3File() {

	}

	public static void main(String[] args) {
		ReadAudioFileTags rmm = new ReadAudioFileTags();
		
		
		rmm.readWithJaudioTagger(getFile("/home/bsinou/Music/toProcess/F00/BAEB.m4a"));
		rmm.readWithJaudioTagger(getFile("/home/bsinou/Music/toProcess/F00/URXH.aif"));
		rmm.readWithMp3Magic(getFile("/home/bsinou/Music/toProcess/F00/ARXC.mp3"));

	}

	private final static String[] fileNames = { "bark.wav", "bell_25.ogg" };

	private static File getFileFromName(String fileName) {
		String baseDir = System.getProperty("user.dir") + "/src/main/resources/sounds/";
		return getFile(baseDir + fileName);
	}

	private static File getFile(String urlStr) {
		try {
			return new File(urlStr);
		} catch (Exception e) {
			throw new RuntimeException("Cannot get file from " + urlStr, e);
		}
	}
}
