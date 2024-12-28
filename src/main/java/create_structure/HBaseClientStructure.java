package create_structure;

import com.github.javafaker.Faker;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class HBaseClientStructure {


    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        HBaseCustomClient client = new HBaseCustomClient(conf);

        // ==============================================================================================
        // CREATE COLLECTION
        // ==============================================================================================
        
        // Create Tabel Berita
        TableName tableBerita = TableName.valueOf("beritadev");
        String[] beritadev = { "main",  "detail" };
        client.deleteTable(tableBerita);
        client.createTable(tableBerita, beritadev);
        
        // Create Tabel Hewan
        TableName tableHewan = TableName.valueOf("hewandev");
        String[] hewandev = { "main", "petugas", "peternak", "kandang", "jenisHewan", "rumpunHewan", "detail" };
        client.deleteTable(tableHewan);
        client.createTable(tableHewan, hewandev);
        
         // Create Tabel Jenis Hewan
        TableName tableJenisHewan = TableName.valueOf("jenishewandev");
        String[] jenishewandev = { "main", "detail" };
        client.deleteTable(tableJenisHewan);
        client.createTable(tableJenisHewan, jenishewandev);

        TableName tableRumpunHewan = TableName.valueOf("rumpunhewandev");
        String[] rumpunhewandev = { "main", "detail" };
        client.deleteTable(tableRumpunHewan);
        client.createTable(tableRumpunHewan, rumpunhewandev);
        
        // Create Tabel Inseminasi
        TableName tableInseminasi = TableName.valueOf("inseminasidev");
        String[] inseminasidev = { "main", "peternak", "hewan", "petugas", "detail" };
        client.deleteTable(tableInseminasi);
        client.createTable(tableInseminasi, inseminasidev);
        
        // Create Tabel Kandang
        TableName tableKandang = TableName.valueOf("kandangdev");
        String[] kandangdev = { "main", "jenisHewan", "peternak", "detail" };
        client.deleteTable(tableKandang);
        client.createTable(tableKandang, kandangdev);
        
        // Create Tabel Kelahiran
        TableName tableKelahiran = TableName.valueOf("kelahirandev");
        String[] kelahirandev = { "main", "peternak", "hewan","kandang", "petugas","inseminasi", "detail" };
        client.deleteTable(tableKelahiran);
        client.createTable(tableKelahiran, kelahirandev);

        // Create Tabel Pengobatan
        TableName tablePengobatan = TableName.valueOf("pengobatandev");
        String[] pengobatandev = { "main", "petugas", "detail" };
        client.deleteTable(tablePengobatan);
        client.createTable(tablePengobatan, pengobatandev);

        // Create Tabel Peternak
        TableName tablePeternak = TableName.valueOf("peternakdev");
        String[] peternakdev = { "main", "userdev", "petugas", "detail" };
        client.deleteTable(tablePeternak);
        client.createTable(tablePeternak, peternakdev);

        // Create Tabel Petugas
        TableName tablePetugas = TableName.valueOf("petugasdev");
        String[] petugasdev = { "main", "userdev", "detail" };
        client.deleteTable(tablePetugas);
        client.createTable(tablePetugas, petugasdev);

        // Create Tabel Pkb
        TableName tablePkb = TableName.valueOf("pkbdev");
        String[] pkbdev = { "main", "peternak", "hewan", "petugas", "detail" };
        client.deleteTable(tablePkb);
        client.createTable(tablePkb, pkbdev);

        // Create Tabel Vaksin
        TableName tableVaksin = TableName.valueOf("vaksindev");
        String[] vaksindev = { "main", "peternak", "hewan", "petugas", "detail" };
        client.deleteTable(tableVaksin);
        client.createTable(tableVaksin, vaksindev);

        // Create Tabel Penghijauan
        TableName tablePenghijauan = TableName.valueOf("penghijauandev");
        String[] penghijauandev = { "main", "detail" };
        client.deleteTable(tablePenghijauan);
        client.createTable(tablePenghijauan, penghijauandev);

        // Create Table Users
        TableName tableUser = TableName.valueOf("userdev");
        String[] userdev = { "main", "detail" };
        client.deleteTable(tableUser);
        client.createTable(tableUser, userdev);

        // seeder
        // time now
        ZoneId zoneId = ZoneId.of("Asia/Jakarta");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        Instant instant = zonedDateTime.toInstant();
        
        // Insert Users
        client.insertRecord(tableUser, "USR001", "main", "id", "USR001");
        client.insertRecord(tableUser, "USR001", "main", "name", "Senja Abdi");
        client.insertRecord(tableUser, "USR001", "main", "username", "admin");
        client.insertRecord(tableUser, "USR001", "main", "email", "admin@gmail.com");
        client.insertRecord(tableUser, "USR001", "main", "password", "$2a$10$SDRWMUk.2fnli0GTmqodJexjRksTw0En98dU8fdKsw7nTbZzMrj.2"); // password
        client.insertRecord(tableUser, "USR001", "main", "role", "1");
     //   client.insertRecord(tableUser, "USR001", "main", "createdAt", "2024-05-14T04:56:23.174Z");
        client.insertRecord(tableUser, "USR001", "detail", "createdAt", "Senja007");

        client.insertRecord(tableUser, "USR002", "main", "id", "USR002");
        client.insertRecord(tableUser, "USR002", "main", "name", "petugas");
        client.insertRecord(tableUser, "USR002", "main", "username", "petugas");
        client.insertRecord(tableUser, "USR002", "main", "email", "petugas@gmail.com");
        client.insertRecord(tableUser, "USR002", "main", "password", "$2a$10$SDRWMUk.2fnli0GTmqodJexjRksTw0En98dU8fdKsw7nTbZzMrj.2"); // password
        client.insertRecord(tableUser, "USR002", "main", "role", "2");
    //    client.insertRecord(tableUser, "USR002", "main", "createdAt", "2024-05-14T04:56:23.174Z");
        client.insertRecord(tableUser, "USR002", "detail", "createdAt", "Senja007");

        client.insertRecord(tableUser, "USR003", "main", "id", "USR003");
        client.insertRecord(tableUser, "USR003", "main", "name", "peternak");
        client.insertRecord(tableUser, "USR003", "main", "username", "peternak");
        client.insertRecord(tableUser, "USR003", "main", "email", "peternak@gmail.com");
        client.insertRecord(tableUser, "USR003", "main", "password", "$2a$10$SDRWMUk.2fnli0GTmqodJexjRksTw0En98dU8fdKsw7nTbZzMrj.2"); // password
        client.insertRecord(tableUser, "USR003", "main", "role", "3");
      //  client.insertRecord(tableUser, "USR003", "main", "createdAt", "2024-05-14T04:56:23.174Z");
        client.insertRecord(tableUser, "USR003", "detail", "createdAt", "Senja007");

        
////        // Insert Petugas 
//client.insertRecord(tablePetugas, "PTG001", "main", "nikPetugas", "PTG001");
//client.insertRecord(tablePetugas, "PTG001", "main", "namaPetugas", "sunaryam");
//client.insertRecord(tablePetugas, "PTG001", "main", "noTelp", "123456789");
//client.insertRecord(tablePetugas, "PTG001", "main", "email", "petugas1@gmail.com");
//client.insertRecord(tablePetugas, "PTG001", "detail", "created_by", "admin");

//client.insertRecord(tablePetugas, "PTG002", "main", "nikPetugas", "NIK002");
//client.insertRecord(tablePetugas, "PTG002", "main", "namaPetugas", "sunardi");
//client.insertRecord(tablePetugas, "PTG002", "main", "noTelp", "987654321");
//client.insertRecord(tablePetugas, "PTG002", "main", "email", "petugas2@gmail.com");
////client.insertRecord(tablePetugas, "PTG002", "user", "id", "USR003"); // Assign user with ID USR003 as the admin who created this petugas
//client.insertRecord(tablePetugas, "PTG002", "detail", "created_by", "admin");
//
//client.insertRecord(tablePetugas, "PTG003", "main", "nikPetugas", "NIK003");
//client.insertRecord(tablePetugas, "PTG003", "main", "namaPetugas", "kimin");
//client.insertRecord(tablePetugas, "PTG003", "main", "noTelp", "555555555");
//client.insertRecord(tablePetugas, "PTG003", "main", "email", "petugas3@gmail.com");
////client.insertRecord(tablePetugas, "PTG003", "user", "id", "USR001"); // Assign user with ID USR001 as the admin who created this petugas
//client.insertRecord(tablePetugas, "PTG003", "detail", "created_by", "admin");

//        client.insertRecord(tablePeternak, "PTK001", "main", "idPeternak", "PTK001");
//        client.insertRecord(tablePeternak, "PTK001", "main", "nikPeternak", "Ptk001");
//        client.insertRecord(tablePeternak, "PTK001", "main", "namaPeternak", "peternak1");
//        client.insertRecord(tablePeternak, "PTK001", "main", "idISIKHNAS", "2211");
//        client.insertRecord(tablePeternak, "PTK001", "main", "lokasi", "Lumajang");
//        client.insertRecord(tablePeternak, "PTK001", "main", "tanggalPendaftaran", "22/02/2022");
//        client.insertRecord(tablePeternak, "PTK001", "petugas", "nikPetugas", "PTG001");
//        client.insertRecord(tablePeternak, "PTK001", "petugas", "namaPetugas", "sunaryam");
//        client.insertRecord(tablePeternak, "PTK001", "petugas", "noTelp", "123456789");
//        client.insertRecord(tablePeternak, "PTK001", "petugas", "email", "petugas1@gmail.com");
//        
//        client.insertRecord(tablePeternak, "PTK002", "main", "idPeternak", "PTK002");
//        client.insertRecord(tablePeternak, "PTK002", "main", "nikPeternak", "Ptk002");
//        client.insertRecord(tablePeternak, "PTK002", "main", "namaPeternak", "peternak2");
//        client.insertRecord(tablePeternak, "PTK002", "main", "idISIKHNAS", "2211");
//        client.insertRecord(tablePeternak, "PTK002", "main", "lokasi", "Lumajang");
//        client.insertRecord(tablePeternak, "PTK002", "main", "tanggalPendaftaran", "22/02/2022");
//        client.insertRecord(tablePeternak, "PTK002", "petugas", "nikPetugas", "PTG001");
//        client.insertRecord(tablePeternak, "PTK002", "petugas", "namaPetugas", "sunaryam");
//        client.insertRecord(tablePeternak, "PTK002", "petugas", "noTelp", "123456789");
//        client.insertRecord(tablePeternak, "PTK002", "petugas", "email", "petugas1@gmail.com");
//        // Insert Jurusan
//        client.insertRecord(tableDepartment, "DP001", "main", "id", "DP001");
//        client.insertRecord(tableDepartment, "DP001", "main", "name", "Jurusan Teknologi Informasi");
//        client.insertRecord(tableDepartment, "DP001", "main", "description", "Ini merupakan jurusan untuk mahasiswa informatika");
//        client.insertRecord(tableDepartment, "DP001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableDepartment, "DP002", "main", "id", "DP002");
//        client.insertRecord(tableDepartment, "DP002", "main", "name", "Jurusan Teknik Mesin");
//        client.insertRecord(tableDepartment, "DP002", "main", "description", "Ini merupakan jurusan untuk mahasiswa teknik mesin");
//        client.insertRecord(tableDepartment, "DP002", "detail", "created_by", "Doyatama");
//
//        // Insert Prodi
//        client.insertRecord(tableStudyProgram, "SP001", "main", "id", "SP001");
//        client.insertRecord(tableStudyProgram, "SP001", "main", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableStudyProgram, "SP001", "main", "description", "Ini merupakan prodi untuk mahasiswa informatika");
//        client.insertRecord(tableStudyProgram, "SP001", "department", "id", "DP001");
//        client.insertRecord(tableStudyProgram, "SP001", "department", "name", "Jurusan Teknologi Informasi");
//        client.insertRecord(tableStudyProgram, "SP001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableStudyProgram, "SP002", "main", "id", "SP002");
//        client.insertRecord(tableStudyProgram, "SP002", "main", "name", "D3 Manajemen Informatika");
//        client.insertRecord(tableStudyProgram, "SP002", "main", "description", "Ini merupakan prodi untuk mahasiswa informatika");
//        client.insertRecord(tableStudyProgram, "SP002", "department", "id", "DP002");
//        client.insertRecord(tableStudyProgram, "SP002", "department", "name", "Jurusan Teknik Mesin");
//        client.insertRecord(tableStudyProgram, "SP002", "detail", "created_by", "Doyatama");
//
//        
//
//        // Insert Religions
//        client.insertRecord(tableReligion, "RLG001", "main", "id", "RLG001");
//        client.insertRecord(tableReligion, "RLG001", "main", "name", "Islam");
//        client.insertRecord(tableReligion, "RLG001", "main", "description", "deskripsi agama islam");
//        client.insertRecord(tableReligion, "RLG001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableReligion, "RLG002", "main", "id", "RLG002");
//        client.insertRecord(tableReligion, "RLG002", "main", "name", "Kristen");
//        client.insertRecord(tableReligion, "RLG002", "main", "description", "deskripsi agama kristen");
//        client.insertRecord(tableReligion, "RLG002", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableReligion, "RLG003", "main", "id", "RLG003");
//        client.insertRecord(tableReligion, "RLG003", "main", "name", "Katolik");
//        client.insertRecord(tableReligion, "RLG003", "main", "description", "deskripsi agama katolik");
//        client.insertRecord(tableReligion, "RLG003", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableReligion, "RLG004", "main", "id", "RLG004");
//        client.insertRecord(tableReligion, "RLG004", "main", "name", "Hindu");
//        client.insertRecord(tableReligion, "RLG004", "main", "description", "deskripsi agama hindu");
//        client.insertRecord(tableReligion, "RLG004", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableReligion, "RLG005", "main", "id", "RLG005");
//        client.insertRecord(tableReligion, "RLG005", "main", "name", "Buddha");
//        client.insertRecord(tableReligion, "RLG005", "main", "description", "deskripsi agama budha");
//        client.insertRecord(tableReligion, "RLG005", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableReligion, "RLG006", "main", "id", "RLG006");
//        client.insertRecord(tableReligion, "RLG006", "main", "name", "Kong Hu Chu");
//        client.insertRecord(tableReligion, "RLG006", "main", "description", "deskripsi agama kong hu chu");
//        client.insertRecord(tableReligion, "RLG006", "detail", "created_by", "Doyatama");
//
//        // Insert Bentuk Pembelajaran
//        client.insertRecord(tableFormLearning, "BP001", "main", "id", "BP001");
//        client.insertRecord(tableFormLearning, "BP001", "main", "name", "Daring");
//        client.insertRecord(tableFormLearning, "BP001", "main", "description", "Pembelajaran dilakukan secara dalam jaringan / online");
//        client.insertRecord(tableFormLearning, "BP001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableFormLearning, "BP002", "main", "id", "BP002");
//        client.insertRecord(tableFormLearning, "BP002", "main", "name", "Luring");
//        client.insertRecord(tableFormLearning, "BP002", "main", "description", "Pembelajaran dilakukan secara diluar jaringan / offline");
//        client.insertRecord(tableFormLearning, "BP002", "detail", "created_by", "Doyatama");
//
//        // Insert Metode Pembelajaran
//        client.insertRecord(tableLearningMethod, "MP001", "main", "id", "MP001");
//        client.insertRecord(tableLearningMethod, "MP001", "main", "name", "Contextual Teaching and Learning (CTL)");
//        client.insertRecord(tableLearningMethod, "MP001", "main", "description", "Pengertian dari CTL");
//        client.insertRecord(tableLearningMethod, "MP001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableLearningMethod, "MP002", "main", "id", "MP002");
//        client.insertRecord(tableLearningMethod, "MP002", "main", "name", "Problem Based Learning");
//        client.insertRecord(tableLearningMethod, "MP002", "main", "description", "Pengertian dari PBL");
//        client.insertRecord(tableLearningMethod, "MP002", "detail", "created_by", "Doyatama");
//
//        // Insert Kriteria Penilaian
//        client.insertRecord(tableAssessmentCriteria, "KP001", "main", "id", "KP001");
//        client.insertRecord(tableAssessmentCriteria, "KP001", "main", "name", "Ketepatan");
//        client.insertRecord(tableAssessmentCriteria, "KP001", "main", "description", "Ketepatan dalam pengumpulan tugas dan kehadiran di kelas");
//        client.insertRecord(tableAssessmentCriteria, "KP001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableAssessmentCriteria, "KP002", "main", "id", "KP002");
//        client.insertRecord(tableAssessmentCriteria, "KP002", "main", "name", "Penugasan");
//        client.insertRecord(tableAssessmentCriteria, "KP002", "main", "description", "Penilaian didasarkan dari penugasan yang diberikan dosen");
//        client.insertRecord(tableAssessmentCriteria, "KP002", "detail", "created_by", "Doyatama");
//
//        // Insert Bentuk Penilaian
//        client.insertRecord(tableAppraisalForm, "AF001", "main", "id", "AF001");
//        client.insertRecord(tableAppraisalForm, "AF001", "main", "name", "Keaktifan diskusi kelompok meliputi bertanya dan menjawab");
//        client.insertRecord(tableAppraisalForm, "AF001", "main", "description", "Keaktifan diskusi kelompok");
//        client.insertRecord(tableAppraisalForm, "AF001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableAppraisalForm, "AF002", "main", "id", "AF002");
//        client.insertRecord(tableAppraisalForm, "AF002", "main", "name", "Ketepatan jawaban tugas");
//        client.insertRecord(tableAppraisalForm, "AF002", "main", "description", "Ketepatan jawaban yang diberikan dari tugas");
//        client.insertRecord(tableAppraisalForm, "AF002", "detail", "created_by", "Doyatama");
//
//        // Insert Subject Group
//        client.insertRecord(tableSubjectGroup, "SG001", "main", "id", "SG001");
//        client.insertRecord(tableSubjectGroup, "SG001", "main", "name", "Big Data");
//        client.insertRecord(tableSubjectGroup, "SG001", "main", "description", "ini kelompok big data");
//        client.insertRecord(tableSubjectGroup, "SG001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableSubjectGroup, "SG002", "main", "id", "SG002");
//        client.insertRecord(tableSubjectGroup, "SG002", "main", "name", "Artificial Inteligent");
//        client.insertRecord(tableSubjectGroup, "SG002", "main", "description", "ini kelompok AI");
//        client.insertRecord(tableSubjectGroup, "SG002", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableSubjectGroup, "SG003", "main", "id", "SG003");
//        client.insertRecord(tableSubjectGroup, "SG003", "main", "name", "Sistem Pendukung Keputusan");
//        client.insertRecord(tableSubjectGroup, "SG003", "main", "description", "ini kelompok SPK");
//        client.insertRecord(tableSubjectGroup, "SG003", "detail", "created_by", "Doyatama");
//
//        // Insert Subject
//        client.insertRecord(tableSubject, "SB001", "main", "id", "SB001");
//        client.insertRecord(tableSubject, "SB001", "main", "name", "Pemrograman Berbasis Object");
//        client.insertRecord(tableSubject, "SB001", "main", "description", "ini deskripsi dari mata kuliah");
//        client.insertRecord(tableSubject, "SB001", "main", "credit_point", "4");
//        client.insertRecord(tableSubject, "SB001", "main", "year_commenced", "2023");
//        client.insertRecord(tableSubject, "SB001", "study_program", "id", "SP001");
//        client.insertRecord(tableSubject, "SB001", "study_program", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableSubject, "SB001", "subject_group", "id", "SG001");
//        client.insertRecord(tableSubject, "SB001", "subject_group", "name", "Big Data");
//        client.insertRecord(tableSubject, "SB001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableSubject, "SB002", "main", "id", "SB002");
//        client.insertRecord(tableSubject, "SB002", "main", "name", "Pemrograman Lanjut");
//        client.insertRecord(tableSubject, "SB002", "main", "description", "ini deskripsi dari mata kuliah");
//        client.insertRecord(tableSubject, "SB002", "main", "credit_point", "4");
//        client.insertRecord(tableSubject, "SB002", "main", "year_commenced", "2023");
//        client.insertRecord(tableSubject, "SB002", "study_program", "id", "SP001");
//        client.insertRecord(tableSubject, "SB002", "study_program", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableSubject, "SB002", "subject_group", "id", "SG001");
//        client.insertRecord(tableSubject, "SB002", "subject_group", "name", "Big Data");
//        client.insertRecord(tableSubject, "SB002", "detail", "created_by", "Doyatama");
//
//        // Insert Lectures
//        client.insertRecord(tableLecture, "LEC001", "main", "id", "LEC001");
//        client.insertRecord(tableLecture, "LEC001", "main", "address", "dosen@gmail.com");
//        client.insertRecord(tableLecture, "LEC001", "main", "date_born", "dosen");
//        client.insertRecord(tableLecture, "LEC001", "main", "gender", "$2a$10$SDRWMUk.2fnli0GTmqodJexjRksTw0En98dU8fdKsw7nTbZzMrj.2"); // password
//        client.insertRecord(tableLecture, "LEC001", "main", "name", "2");
//        client.insertRecord(tableLecture, "LEC001", "main", "nidn", "2");
//        client.insertRecord(tableLecture, "LEC001", "main", "phone", "2");
//        client.insertRecord(tableLecture, "LEC001", "main", "place_born", "2");
//        client.insertRecord(tableLecture, "LEC001", "main", "status", "Dosen");
//        client.insertRecord(tableLecture, "LEC001", "religion", "id", "RLG001");
//        client.insertRecord(tableLecture, "LEC001", "religion", "name", "Islam");
//        client.insertRecord(tableLecture, "LEC001", "study_program", "id", "SP001");
//        client.insertRecord(tableLecture, "LEC001", "study_program", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableLecture, "LEC001", "user", "id", "USR001");
//        client.insertRecord(tableLecture, "LEC001", "user", "name", "dosen");
//        client.insertRecord(tableLecture, "LEC001", "user", "email", "dosen@gmail.com");
//        client.insertRecord(tableLecture, "LEC001", "user", "username", "dosen");
//        client.insertRecord(tableLecture, "LEC001", "detail", "created_by", "Doyatama");
//
//        client.insertRecord(tableLecture, "LEC002", "main", "id", "LEC002");
//        client.insertRecord(tableLecture, "LEC002", "main", "address", "dosen2@gmail.com");
//        client.insertRecord(tableLecture, "LEC002", "main", "date_born", "dosen2");
//        client.insertRecord(tableLecture, "LEC002", "main", "gender", "$2a$10$SDRWMUk.2fnli0GTmqodJexjRksTw0En98dU8fdKsw7nTbZzMrj.2"); // password
//        client.insertRecord(tableLecture, "LEC002", "main", "name", "2");
//        client.insertRecord(tableLecture, "LEC002", "main", "nidn", "2");
//        client.insertRecord(tableLecture, "LEC002", "main", "phone", "2");
//        client.insertRecord(tableLecture, "LEC002", "main", "place_born", "2");
//        client.insertRecord(tableLecture, "LEC002", "main", "status", "Dosen");
//        client.insertRecord(tableLecture, "LEC002", "religion", "id", "RLG001");
//        client.insertRecord(tableLecture, "LEC002", "religion", "name", "Islam");
//        client.insertRecord(tableLecture, "LEC002", "study_program", "id", "SP001");
//        client.insertRecord(tableLecture, "LEC002", "study_program", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableLecture, "LEC002", "user", "id", "USR002");
//        client.insertRecord(tableLecture, "LEC002", "user", "name", "dosen");
//        client.insertRecord(tableLecture, "LEC002", "user", "email", "dosen@gmail.com");
//        client.insertRecord(tableLecture, "LEC002", "user", "username", "dosen");
//        client.insertRecord(tableLecture, "LEC002", "detail", "created_by", "Doyatama");
//
//        // Learning Media
//        // Software
//        client.insertRecord(tableLearningMedia, "LM001", "main", "id", "LM001");
//        client.insertRecord(tableLearningMedia, "LM001", "main", "name", "Netbeans");
//        client.insertRecord(tableLearningMedia, "LM001", "main", "description", "ini deskripsi netbeans");
//        client.insertRecord(tableLearningMedia, "LM001", "main", "type", "1");
//        client.insertRecord(tableLearningMedia, "LM001", "detail", "created_by", "Doyatama");
//
//        // Hardware
//        client.insertRecord(tableLearningMedia, "LM002", "main", "id", "LM002");
//        client.insertRecord(tableLearningMedia, "LM002", "main", "name", "PC / Komputer");
//        client.insertRecord(tableLearningMedia, "LM002", "main", "description", "ini deskripsi pc / komputer");
//        client.insertRecord(tableLearningMedia, "LM002", "main", "type", "2");
//        client.insertRecord(tableLearningMedia, "LM002", "detail", "created_by", "Doyatama");
//
//        // RPS
//        client.insertRecord(tableRPS, "RPS001", "main", "id", "RPS001");
//        client.insertRecord(tableRPS, "RPS001", "main", "name", "Dummy RPS");
//        client.insertRecord(tableRPS, "RPS001", "main", "sks", "3");
//        client.insertRecord(tableRPS, "RPS001", "main", "semester", "3");
//        client.insertRecord(tableRPS, "RPS001", "main", "cpl_prodi", "Dummy CPL Prodi");
//        client.insertRecord(tableRPS, "RPS001", "main", "cpl_mk", "Dummy CPL MK");
//        client.insertRecord(tableRPS, "RPS001", "study_program", "id", "SP001");
//        client.insertRecord(tableRPS, "RPS001", "study_program", "name", "D4 Teknik Informatika");
//        client.insertRecord(tableRPS, "RPS001", "subject", "id", "SB001");
//        client.insertRecord(tableRPS, "RPS001", "subject", "name", "Pemrograman Berbasis Object");
//        client.insertRecord(tableRPS, "RPS001", "ka_study_program", "id", "LEC001");
//        client.insertRecord(tableRPS, "RPS001", "ka_study_program", "name", "2");
//        client.insertRecord(tableRPS, "RPS001", "detail", "created_by", "Doyatama");
//        client.insertRecord(tableRPS, "RPS001", "detail", "created_at", instant.toString());
//
//        // RPS Detail
//        client.insertRecord(tableRPSDetail, "RPSD001", "main", "id", "RPSD001");
//        client.insertRecord(tableRPSDetail, "RPSD001", "main", "week", "2");
//        client.insertRecord(tableRPSDetail, "RPSD001", "rps", "id", "RPS001");
//        client.insertRecord(tableRPSDetail, "RPSD001", "rps", "name", "Dummy RPS");
//        client.insertRecord(tableRPSDetail, "RPSD001", "main", "sub_cp_mk", "Dummy Sub CP MK");
//        client.insertRecord(tableRPSDetail, "RPSD001", "learning_materials", "lm_1", "Dummy Learning Material");
//        client.insertRecord(tableRPSDetail, "RPSD001", "form_learning", "id", "BP001");
//        client.insertRecord(tableRPSDetail, "RPSD001", "form_learning", "name", "Daring");
//        client.insertRecord(tableRPSDetail, "RPSD001", "assignments", "lm_1",  "Dummy Penugasan");
//        client.insertRecord(tableRPSDetail, "RPSD001", "estimated_times", "et_1", "Dummy Estimasi Waktu");
//        client.insertRecord(tableRPSDetail, "RPSD001", "student_learning_experiences", "sle_1", "Dummy Pengalaman Mahasiswa");
//        client.insertRecord(tableRPSDetail, "RPSD001", "assessment_indicators", "ai_1", "Dummy Assessment Indikator");
//        client.insertRecord(tableRPSDetail, "RPSD001", "main", "weight", "3");
//        client.insertRecord(tableRPSDetail, "RPSD001", "detail", "created_by", "Doyatama");
//        client.insertRecord(tableRPSDetail, "RPSD001", "detail", "created_at", instant.toString());

//        for (int i = 0; i < 5000000; i++) {
//            Faker faker = new Faker();
//            String[] typeQuestion = {"VIDEO", "AUDIO", "IMAGE", "NORMAL"};
//            String[] typeAnswer = {"MULTIPLE_CHOICE", "BOOLEAN", "COMPLETION", "MATCHING", "ESSAY"};
//            String typeQ = typeQuestion[faker.random().nextInt(typeQuestion.length)];
//            String path = "";
////            client.insertRecord(tableQuestion, "QST"+i, "main", "id", "QST"+i);
////            client.insertRecord(tableQuestion, "QST"+i, "main", "title", faker.lorem().sentence());
////            client.insertRecord(tableQuestion, "QST"+i, "main", "description", faker.lorem().sentence());
////            client.insertRecord(tableQuestion, "QST"+i, "main", "question_type", typeQ);
////            client.insertRecord(tableQuestion, "QST"+i, "main", "answer_type", typeAnswer[faker.random().nextInt(typeAnswer.length)]);
//            switch (typeQ) {
//                case "VIDEO":
//                    path = "webhdfs/v1/questions/video_dummy.mp4?op=OPEN";
//                    break;
//                case "AUDIO":
//                    path = "webhdfs/v1/questions/audio_dummy.mp3?op=OPEN";
//                    break;
//                case "IMAGE":
//                    path = "webhdfs/v1/questions/image_dummy.png?op=OPEN";
//                    break;
//                case "NORMAL":
//                    path = "none";
//                    break;
//            }
////            client.insertRecord(tableQuestion, "QST"+i, "main", "file_path", path);
////            client.insertRecord(tableQuestion, "QST"+i, "rps_detail", "id", "RPSD001");
////            client.insertRecord(tableQuestion, "QST"+i, "rps_detail", "sub_cp_mk", "Dummy Sub CP MK");
////            client.insertRecord(tableQuestion, "QST"+i, "detail", "rps_id", "RPS001");
////            client.insertRecord(tableQuestion, "QST"+i, "detail", "created_by", "Doyatama");
//    
//        }
    }
}