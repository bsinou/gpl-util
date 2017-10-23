package net.sinou.tools.media.tagger;

import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class Mp3TagManager implements TagManager {
	private final Logger logger = LoggerFactory.getLogger(Mp3TagManager.class);

	@Override
	public Map<String, String> readTags(Path path) {
		try {
			if (logger.isDebugEnabled())
				logger.debug("=== Getting info for " + path.getFileName() + " using Mp3 Magic");
			Mp3File mp3file = new Mp3File(path);
			if (mp3file.hasId3v1Tag())
				readId3v1(mp3file);
			if (mp3file.hasId3v2Tag())
				readId3v2(mp3file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	@Override
	public void setTags(Path path, Map<String, String> tags) {
		throw new RuntimeException("Unimplemented method");
	}
}
