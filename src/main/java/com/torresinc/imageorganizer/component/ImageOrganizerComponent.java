package com.torresinc.imageorganizer.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.torresinc.imageorganizer.service.ImageOrganizerService;

@Component
public class ImageOrganizerComponent implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ImageOrganizerComponent.class);

  @Value("${dir}")
  private String dir;

  @Value("${year}")
  private int year = 2020;

  private final ImageOrganizerService imageOrganizerService;

  public ImageOrganizerComponent(ImageOrganizerService fileScannerService) {
    this.imageOrganizerService = fileScannerService;
  }

  @Override
  public void run(String... args) throws Exception {
    logger.info("Calling imageOrganizerService.organizeImages()");
    imageOrganizerService.organizeImages(dir, year);
    logger.info("imageOrganizerService.organizeImages() called");
  }
  
}
