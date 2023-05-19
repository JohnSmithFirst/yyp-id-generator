package com.yyp.id.generator.service.bussiness;

import com.yyp.id.generator.bussiness.dto.IdGeneratorResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class IdGeneratorServiceTest {

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Test
    void test() {
        String generator = "test7";
        IdGeneratorResult result = null;
        for (int i = 0; i < 2; i++) {
            result = idGeneratorService.generateId(generator, result);
            long currentId;
            while ((currentId = result.currentId()) >= 0L) {
                log.info("generator:{} 获取到currentId:{}", generator, currentId);
            }
        }
    }
}
