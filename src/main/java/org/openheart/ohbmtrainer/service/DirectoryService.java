package org.openheart.ohbmtrainer.service;

import java.util.List;
import java.util.Map;

public interface DirectoryService {
    Map<Integer, List<String>> getMappedFilenames();
}
