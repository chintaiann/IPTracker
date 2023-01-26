package com.iptracker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.iptracker.models.Address;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
public class UploadController {
	@PostMapping("/upload")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file,Model model) {
		if (file.isEmpty()) { 
			model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
            } else { 
            	 // parse CSV file to create a list of `User` objects
                try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                    // create csv bean reader
                    CsvToBean<Address> csvToBean = new CsvToBeanBuilder(reader)
                            .withType(Address.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

                    // convert `CsvToBean` object to list of users
                    List<Address> addresses = csvToBean.parse();

                    // TODO: save users in DB?

                    // save users list on model
                    model.addAttribute("addresses", addresses);
                    model.addAttribute("status", true);

                } catch (Exception ex) {
                    model.addAttribute("message", "An error occurred while processing the CSV file.");
                    model.addAttribute("status", false);
                }
            }

            return "file-upload-status";
            }
	}
	

