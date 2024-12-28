//package com.ternak.sapi.config;
//
//public class PathConfig {
//    public static String storagePath = "E:/Skripsi";
//}


package com.ternak.sapi.config;

import java.io.File;

public class PathConfig {
    // Menggunakan direktori home pengguna dan menambahkan subdirektori "fotoPath"
    public static String storagePath = System.getProperty("user.home") + File.separator + "fotoPath";

    // Method untuk membuat direktori jika belum ada
    public static void createStoragePathIfNotExists() {
        File directory = new File(storagePath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Membuat direktori termasuk subdirektori jika belum ada
            if (created) {
                System.out.println("Directory created: " + storagePath);
            } else {
                System.err.println("Failed to create directory: " + storagePath);
            }
        } else {
            System.out.println("Directory already exists: " + storagePath);
        }
    }
}
