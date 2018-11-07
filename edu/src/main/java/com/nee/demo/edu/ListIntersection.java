package com.nee.demo.edu;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Data
@ToString
@Slf4j
public class ListIntersection {


    public static void main(String[] args) {
        List<String> userId = new ArrayList<>();
        Map<String, List<User>> userMap = new HashMap<>();
        try {
            Long startTime = System.currentTimeMillis();
            String fileName = "/Users/heikki/Documents/user_result/result.csv";
            String encoding = "UTF-8";
            File file = new File(fileName);
            Long filelength = file.length();
            byte[] filecontent = new byte[filelength.intValue()];
            try {
                FileInputStream in = new FileInputStream(file);
                in.read(filecontent);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String content = new String(filecontent, encoding);
            filecontent = null;
            String[] lines = content.split("\n");
            content = null;
            for (String line : lines) {
                User user = new User(line);
                userMap.computeIfAbsent(user.getU_id(), k -> new ArrayList<>());
                userMap.get(user.getU_id()).add(user);
            }

            log.info("spent {} seconds load data", (System.currentTimeMillis() - startTime) / 1000);

            userMap.forEach((u_id, users) -> {

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

@Data
class User {
    private String u_id;
    private String ad;
    private String begin;
    private String end;

    User(String content) {
        String[] s = content.split(",");
        u_id = s[0];
        ad = s[1];
        begin = s[2];
        end = s[3];
    }
}
