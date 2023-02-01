package uz.mutalov.onlinemarket.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.mutalov.onlinemarket.exceptions.NotFoundException;
import uz.mutalov.onlinemarket.response.DataDTO;
import uz.mutalov.onlinemarket.response.ResponseEntity;
import uz.mutalov.onlinemarket.service.base.BaseService;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService implements BaseService {

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("picturesaver-61bc7.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(getCredentials());
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/picturesaver-61bc7.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file;
        if (Objects.equals(multipartFile.getContentType(), "image/png")) {
            file = File.createTempFile("upload/" + fileName, ".png");
        } else if (Objects.equals(multipartFile.getContentType(), "image/jpeg")) {
            file = File.createTempFile("upload/" + fileName, ".jpg");
        } else
            file = File.createTempFile("upload/" + fileName, ".jpeg");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }

        return file;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public ResponseEntity<DataDTO<String>> upload(MultipartFile picture) {
        String PATH;
        try {
            String fileName = picture.getOriginalFilename();                        // to get original file name
            assert fileName != null;
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            File file = convertToFile(picture, fileName);
            PATH = uploadFile(file, fileName);
        } catch (IOException e) {
            throw new NotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(new DataDTO<>(PATH));
    }

    private InputStream getCredentials() {
        String credentials = """
                {
                  "type": "service_account",
                  "project_id": "picturesaver-61bc7",
                  "private_key_id": "aac388e1ff2d62cac59f4859480cfdf6b165cdd7",
                  "private_key": "-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC4xadg5Xw/cfF1\\n+ShyTlLySKz01isy+3qGuEa5LSpFNCVYi/Em7uHnOg/T98fSRMLh7UDASMdKcskp\\nAqiupNvioF8t9qJeFS6I+OnM7V62aJGytUTWsu97qM9JXaUcQtzSk1/pnn6qeFtr\\n4FDFQTdQIihzoyPI5ZNktEisWCkYZdO2aqZP6cZNvUqQWJJGYTQoV/yneWgRjbVB\\nSSJFHQxUTKTqRwh5nMvESXmS/Z25CF9IvsTV0x6p1s9RPO5NnxL+ncZ593BD7cPd\\nEUG6+0+RNwKCsfbUIHn3MskEw0jhss+salxAnp/3TYbPgXA1cAc1k9DgkHaEoVbw\\nAoHOLBKTAgMBAAECggEAEkdgvZHg7sMuPfDTctYTbUCJ9lam7g45cCZpaLGdIud7\\nt6dwczI39ZCljDSmTJjc0U46lPXI3AaVppGgGlbYzIdM+IETovcOzszBDwgp0dBv\\nyXFr8/QTrkoO55UfW7e0R2io7mBC655TJ6Ld8AWkRoXP4VCxnj2H4p3V1VGLZIxS\\n2O5aEhnW3o1CTxnPeBAER7xe82r5MyUfArgvNqoIp2BzgMTjLiaBKpSUN4LNflKo\\n83pKKXOStb5agCVcHcG4spETsZlUxW5oKGhYlkZzT+7orDZ3fWerCovSSsbDVQSV\\ndO5iSz6Hd5UJHUO0WVVCMoVWg26kYJf1BnT/zyBWsQKBgQD5A8Jk18cPL/XD1qF8\\nNNFi+OXqOD3NUzu7JDIo8nAqVwcjYfsUS5IK/FhPKEl3Lwn43leoYAS/dFvdsmjg\\nLUo8b7lGV8Yu5hob6q9sUkoG0tBuezO3kSghLV2lv5emUQbzJSv8aa8bT28zBIHP\\nEuKoG8CNKTV6Rd2kRvhTuKVELQKBgQC99I0g/nhVwUxm1T7KLonT/EXEoR6fsAo8\\n+478vOVLpoRVLlva/+HfZ9E0DD1WYLY45WsFdps32vaGYihBSD6uiNIW/toA18Pn\\nfKRJ3iKZOiQB6eLr5hs1WRRcbA/VKxc3yT51Jnr0Q8Pe4GuSnaiHrLsjJkPaqOOk\\n0uuOB58pvwKBgEk44MWUaZU+rg7bIvsJ8PQgakL4LLwemHbMCu/dcjwRASlXW2Q5\\nYIVYdIM6PngMXetPPgllyp+dkJHdL1eUGbsxwxI7JyyNPuoslU7N17Sv8tg55Bg7\\nV8iBtYLSlr4yH1AKAygiz3nBXtpqnd0NGZcDKhiYIICG4Mi4kbbUmcw1AoGBAKtS\\nOUccu6N8Z1AH3E21k5Gh4brqJhiYSWOe7uN76sJEOO4qlaX7StiXZdOvhlK1fyPp\\nppCS3IOP9PBW7z+RzRbVR8Z9rFk+QO4zOGl283WHPpqLO6LPMpL2+KoyvETiTFqA\\nIti+D/7mb25AskTV9P4JB+83v9Mip84sfU7oCOE5AoGAPIOoTQcrIFvLV9WH0pLt\\nFQtEsA0Z4m1wKUX8XcONeBo8cW0oj+CyvUFvM/0S/0obqhOJqmmw8yAkjF32v0U8\\nQXHluIya+GORepHOdHnnmM9xRcIT40ScY1F/O/ypO6YxWYfXncpSY26ypx1BUd/K\\n5NvdCqNdZYTptvMrhGKe+gc=\\n-----END PRIVATE KEY-----\\n",
                  "client_email": "firebase-adminsdk-vk2of@picturesaver-61bc7.iam.gserviceaccount.com",
                  "client_id": "106726833929722490017",
                  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
                  "token_uri": "https://oauth2.googleapis.com/token",
                  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
                  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-vk2of%40picturesaver-61bc7.iam.gserviceaccount.com"
                }
                                
                """;
        return new ByteArrayInputStream(credentials.getBytes
                (StandardCharsets.UTF_8));
    }
}


