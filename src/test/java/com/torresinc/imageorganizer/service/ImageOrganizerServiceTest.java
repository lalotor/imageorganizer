package com.torresinc.imageorganizer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ImageOrganizerServiceTest {

  private static final Logger logger = Logger.getLogger(ImageOrganizerService.class.getName());

  @Test
  void testOrganizeImages(@TempDir Path tempDir) throws IOException {
    // Create test files
    Path file1 = Files.createFile(tempDir.resolve("file1.jpg"));
    Path file2 = Files.createFile(tempDir.resolve("file2.jpg"));
    Path file3 = Files.createFile(tempDir.resolve("file3.jpg"));

    // Set last modified time to 2022-01-01
    Files.setLastModifiedTime(file1, FileTime.from(LocalDate.of(2022, 4, 4).atStartOfDay().toInstant(ZoneOffset.UTC)));
    Files.setLastModifiedTime(file2, FileTime.from(LocalDate.of(2022, 5, 3).atStartOfDay().toInstant(ZoneOffset.UTC)));
    Files.setLastModifiedTime(file3,
        FileTime.from(LocalDate.of(2022, 11, 15).atStartOfDay().toInstant(ZoneOffset.UTC)));

    // Create ImageOrganizerService instance
    ImageOrganizerService service = new ImageOrganizerService();

    // Call method
    service.organizeImages(tempDir.toString(), 2022);

    // Check that files were moved to correct directories
    assertThat(Files.exists(tempDir.resolve("04").resolve(file1.getFileName()))).isTrue();
    assertThat(Files.exists(tempDir.resolve("05").resolve(file2.getFileName()))).isTrue();
    assertThat(Files.exists(tempDir.resolve("11").resolve(file3.getFileName()))).isTrue();
  }

  @Test
  void testOrganizeImagesNoImages(@TempDir Path tempDir) throws IOException {
    // Create ImageOrganizerService instance
    ImageOrganizerService service = new ImageOrganizerService();

    // Call method
    service.organizeImages(tempDir.toString(), 2022);

    // Check that no directories were created
    assertThat(Files.exists(tempDir.resolve("01"))).isFalse();
    assertThat(Files.exists(tempDir.resolve("02"))).isFalse();
    assertThat(Files.exists(tempDir.resolve("12"))).isFalse();
  }

  @Test
  void testOrganizeImagesOneImage(@TempDir Path tempDir) throws IOException {
    // Create test file
    Path file1 = Files.createFile(tempDir.resolve("file1.jpg"));

    // Set last modified time to 2022-01-01
    Files.setLastModifiedTime(file1, FileTime.from(LocalDate.of(2022, 6, 6).atStartOfDay().toInstant(ZoneOffset.UTC)));

    // Create ImageOrganizerService instance
    ImageOrganizerService service = new ImageOrganizerService();

    // Call method
    service.organizeImages(tempDir.toString(), 2022);

    // Check that file was moved to correct directory
    assertThat(Files.exists(tempDir.resolve("06").resolve(file1.getFileName()))).isTrue();
  }

}
