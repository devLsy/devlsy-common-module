package study.thboard2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;
import study.thboard2.domain.vo.FileVo;
import study.thboard2.service.FileService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 미리보기
     * @param fileNo
     * @return
     */
    @RequestMapping("/images/{fileNo}")
    @ResponseBody
    public Resource downloadImage(@PathVariable Integer fileNo) throws IOException, Exception {
        FileVo fileDetail = fileService.getFileDetail(fileNo);
        UrlResource resource = null;
        if(fileDetail != null) resource = new UrlResource("file:" + fileDetail.getFilePath());
        return resource;
    }

    /**
     * 첨부파일 다운로드
     * @param fileNo
     * @return
     * @throws IOException
     * @throws Exception
     */
    @RequestMapping("/download/{fileNo}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileNo) throws IOException, Exception{
        FileVo fileDetail = fileService.getFileDetail(fileNo);
        UrlResource resource = null;

        if(fileDetail != null) resource = new UrlResource("file:" + fileDetail.getFilePath());
        //한글 관련 인코딩
        String encodedFileName = UriUtils.encode(fileDetail.getFileOrgName(), StandardCharsets.UTF_8);
        //파일 다운로드 대화상자 표시
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);

    }
}
