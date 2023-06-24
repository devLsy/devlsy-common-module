//package study.dev.thboard3.cmm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;
import java.util.List;

@Slf4j
public class ValidatorUtils {

    /**
     * BindingResult에 담긴 에러 출력
     * @param className
     * @param br
     * @throws ValidationException
     */
    public static void invokeErrors(String className, BindingResult br) throws ValidationException {

        log.error("Validation Error Class => [{}]", className);

        List<ObjectError> errorList = br.getAllErrors();

        for (ObjectError error : errorList) {
            log.info("=========================");
            log.info("error List");
            log.error(error.toString());
            log.info("=========================");
        }

        throw new ValidationException("validation failed");
    }
}
