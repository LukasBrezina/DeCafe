package com.example.decafe.utility;

import java.io.File;

public class MusicFileBuilder {

    public static String buildMusicFile(String filename) {
        return new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" +
                File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator +
                "decafe" + File.separator + filename;
    }

}
