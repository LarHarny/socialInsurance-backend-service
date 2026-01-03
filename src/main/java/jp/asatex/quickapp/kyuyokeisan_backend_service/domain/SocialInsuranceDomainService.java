package jp.asatex.quickapp.kyuyokeisan_backend_service.domain;

import jp.asatex.quickapp.kyuyokeisan_backend_service.domain.dto.SocialInsuranceDomainDto;
import reactor.core.publisher.Mono;

/**
 * 社会保险 Domain Service 接口
 * 提供社会保险业务逻辑处理的接口定义
 */
public interface SocialInsuranceDomainService {

    /**
     * 查询社会保险金额
     * 根据月薪和年龄计算社会保险费用
     *
     * @param monthlySalary 月薪
     * @param age 年龄
     * @return Mono<SocialInsuranceDomainDto> 社会保险金额DTO
     */
    Mono<SocialInsuranceDomainDto> socialInsuranceQuery(Integer monthlySalary, Integer age);
}

