package com.torresinc.imageorganizer.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageOrganizerService {

  private static final Logger logger = LoggerFactory.getLogger(ImageOrganizerService.class);

  public void organizeImages(String dir, int year) {
    logger.info("ImageOrganizerService.organizeImages(), args: {}", dir);
    Path path = Paths.get(dir);

    if (!(Files.exists(path) && Files.isDirectory(path))) {
      logger.info("Path must exist and be a directory: {}", dir);
    }

    try (Stream<Path> stream = Files.list(path)) {
      stream.filter(Files::isRegularFile)
          .forEach(childPath -> {
            try {
              BasicFileAttributes data = Files.readAttributes(childPath, BasicFileAttributes.class);
              LocalDate date = Instant.ofEpochMilli(data.lastModifiedTime().toMillis()).atZone(ZoneId.systemDefault())
                  .toLocalDate();
              if (date.getYear() == year) {
                String month = String.format("%02d", date.getMonthValue());
                Path monthPath = Paths.get(dir, month);
                Files.createDirectories(monthPath);
                Path newChildPath = monthPath.resolve(childPath.getFileName().toString());
                logger.info("New path: {}", newChildPath);
                Files.move(childPath, newChildPath);
              } else {
                logger.info("File year does not match {} {} {}", childPath.getFileName(), date.getYear(), year);
              }
            }
            catch (IOException e) {
              logger.error("IO error", e);
            }
          });
    }
    catch (IOException e) {
      logger.error("IO error", e);
    }
  }

}
