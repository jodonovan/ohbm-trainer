package org.openheart.ohbmtrainer.rest.controller;

import org.openheart.ohbmtrainer.rest.domain.RefIdRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final Logger log = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("/{refId}")
    public ResponseEntity<?> getImage(@PathVariable UUID refId) {
        RefIdRest refIdRest = new RefIdRest();
        refIdRest.setRefId(refId);

//        return ResponseEntity.ok().body(Arrays.asList(refIdRest, refIdRest));
        return ResponseEntity.ok().body(refIdRest);
    }
}
