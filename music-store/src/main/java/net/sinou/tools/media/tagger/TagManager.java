package net.sinou.tools.media.tagger;

import java.nio.file.Path;
import java.util.Map;

/** Read and set Tags on audio files depending on the format */
public interface TagManager {

	Map<String, String> readTags(Path path);

	void setTags(Path path, Map<String, String> tags);

}
