package com.covid19.match.external.amazon.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.covid19.match.external.amazon.enums.Regions;
import com.covid19.match.external.amazon.enums.S3Buckets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import static com.covid19.match.utils.DateUtils.getDateToString;

@Service
@Slf4j
public class UploadService {
    private AmazonS3 amazonS3;
    private String bucketName;

    public UploadService(AmazonS3 amazonS3, Regions region) {
        this.amazonS3 = amazonS3;
        this.bucketName = Stream.of(S3Buckets.values())
                .filter(b -> region.equals(b.getRegion()))
                .findFirst()
                .map(S3Buckets::getName)
                .orElse(null);
    }

    public String uploadFile(MultipartFile file) {
        try {
            File convertedFile = convertMultiPartToFile(file);
            String url = uploadFile(convertedFile);
            Files.delete(convertedFile.toPath());
            return url;
        } catch (Exception ex) {
            log.error("File upload fail {}", ex);
        }

        return null;
    }

    public String uploadFile(File file) {

        // Upload a file as a new object with ContentType and title specified.
        String uuid = UUID.randomUUID().toString();
        String key = String.join("/", getDateToString(new Date()), uuid + "-" + file.getName());
        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
        ObjectMetadata metadata = new ObjectMetadata();
        try {
            metadata.setContentType(Files.probeContentType(file.toPath()));
        } catch (Exception ex) {

        }
        metadata.addUserMetadata("name", file.getName());
        request.setMetadata(metadata);
        try {
            PutObjectResult putObjectResult = amazonS3.putObject(request);
            log.info("Upload is chargerged {}", putObjectResult.isRequesterCharged());

            return amazonS3.getUrl(bucketName, key).toExternalForm();
        } catch (SdkClientException ex) {
            log.error("Upload to amazon failed", ex);
        }

        return null;

    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File covertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(covertedFile);
        fos.write(file.getBytes());
        fos.close();
        return covertedFile;
    }
}
