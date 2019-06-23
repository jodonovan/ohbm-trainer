package org.openheart.ohbmtrainer.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DirectoryService {
    Map<Integer, List<String>> getMappedFilenames() throws IOException;
}
