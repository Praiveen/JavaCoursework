package com.example.PcShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class DatabaseLoader {

    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    @Autowired
    public DatabaseLoader(JdbcTemplate jdbcTemplate, ResourceLoader resourceLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceLoader = resourceLoader;
    }

    public void loadSqlFileIfNeeded() throws IOException {
        if (!dataExistsInDatabase()) {
        try {
            Resource resource = resourceLoader.getResource("classpath:data.sql");
            String sql = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            System.err.println("Error loading SQL data: " + e.getMessage());
        }
        }
    }

//    public void loadSqlFileIfNeeded() throws IOException {
//        try {
//            Resource resource = resourceLoader.getResource("classpath:data.sql");
//            String sql = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
//            jdbcTemplate.execute(sql);
//        } catch (Exception e) {
//            System.err.println("Error loading SQL data: " + e.getMessage());
//        }
//
//    }

    private boolean dataExistsInDatabase() {
        String sql = "SELECT COUNT(*) FROM component";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count > 0;
    }
}

