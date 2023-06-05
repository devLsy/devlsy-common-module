package study.thboard2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import study.thboard2.domain.vo.FileVo;

import java.util.List;

@Repository @Mapper
public interface FileMapper {

    /* 파일 정보 저장 */
    void insertFile(FileVo fileVo);

    /* 파일 정보 상세 */
    FileVo selectFileDetail(@Param("fileNo") Integer fileNo);

    /* 파일 목록 조회 */
    List<FileVo> selectFileList(@Param("boardNo") Integer boardNo);

    /* 파일 정보 수정 */
    void updateFile(FileVo fileVo);

    /* 파일 정보 삭제 */
    void deleteFile(@Param("fileNo") Integer fileNo, @Param("boardNo") Integer boardNo);
}
