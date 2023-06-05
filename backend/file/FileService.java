package study.thboard2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import study.thboard2.domain.vo.FileVo;
import study.thboard2.mapper.FileMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static study.thboard2.common.utils.ValidationUtil.invokeErrors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileMapper fileMapper;

    @Value("${file.path}")
    private String filePath;

    /**
     * 첨부파일 저장
     * @param files
     * @param boardNo
     */
    @Transactional
    public void saveFile(List<MultipartFile> files, Integer boardNo, BindingResult br) throws IOException, Exception {

        //parameter 검증 실패 시
        if (br.hasErrors()) {
            log.info("검증 실패");
            invokeErrors(this.getClass().getName(), br);
        }

        if (!files.isEmpty()) {
            FileVo fileVo;

            for (MultipartFile file : files) {
                //원본파일명
                String fileOrgName = file.getOriginalFilename();
                log.info("file orgName = [{}]", file.getOriginalFilename());
                //파일 UUID
                String fileUUid = UUID.randomUUID().toString();
                //파일 확장자
                String extension = fileOrgName.substring(fileOrgName.lastIndexOf("."));
                //서버에 저장될 파일명(UUID+확장자)
                String saveFileName = fileUUid + extension;
                //파일 저장 경로
                String filePath = this.filePath + saveFileName;

                //파일 서버 저장
                file.transferTo(new File(filePath));
                //파일정보 DB 저장
                fileVo = new FileVo();
                fileVo.setBoardNo(boardNo);
                fileVo.setFileOrgName(fileOrgName);
                fileVo.setFilePath(filePath);
                fileVo.setFileSize((int) file.getSize());
                fileMapper.insertFile(fileVo);
                }
            }
    }

    /**
     * 파일 정보 상세
     * @param fileNo
     * @return
     */
    public FileVo getFileDetail(int fileNo) throws Exception{
        return fileMapper.selectFileDetail(fileNo);
    }

    /**
     * 파일 목록 조회
     * @param boardNo
     * @return
     * @throws Exception
     */
    public List<FileVo> getFileList(int boardNo) throws Exception{
        return fileMapper.selectFileList(boardNo);
    }
}
