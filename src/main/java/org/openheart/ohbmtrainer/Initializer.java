package org.openheart.ohbmtrainer;

import org.openheart.ohbmtrainer.service.DirectoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements CommandLineRunner {
    private DirectoryService directoryService;

    public Initializer(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        directoryService.getMappedFilenames();
    }
}
