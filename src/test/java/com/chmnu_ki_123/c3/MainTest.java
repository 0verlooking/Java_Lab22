package com.chmnu_ki_123.c3;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String TEST_SOURCE_FILE = "testSourceFile.txt";
    private static final String TEST_TARGET_DIR = "testTargetDir";

    @BeforeEach
    void setUp() throws IOException {
        Files.writeString(Path.of(TEST_SOURCE_FILE), "Це тестовий файл.");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_SOURCE_FILE));
        Path targetDirPath = Path.of(TEST_TARGET_DIR);
        if (Files.exists(targetDirPath)) {
            Files.walk(targetDirPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    void testCopyFileSuccessful() throws IOException {
        Path copiedFilePath = Main.copyFile(TEST_SOURCE_FILE, TEST_TARGET_DIR);

        assertTrue(Files.exists(copiedFilePath));
        assertEquals("Це тестовий файл.", Files.readString(copiedFilePath));
    }

    @Test
    void testCopyFileSourceNotExist() {
        assertThrows(IOException.class, () -> Main.copyFile("nonexistent.txt", TEST_TARGET_DIR));
    }

    @Test
    void testCopyFileTargetIsNotDirectory() {
        assertThrows(IOException.class, () -> Main.copyFile(TEST_SOURCE_FILE, TEST_SOURCE_FILE));
    }
}

