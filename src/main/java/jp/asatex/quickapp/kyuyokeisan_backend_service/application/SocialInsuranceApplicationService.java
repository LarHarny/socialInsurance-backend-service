package jp.asatex.quickapp.kyuyokeisan_backend_service.application;

import jp.asatex.quickapp.kyuyokeisan_backend_service.application.dto.SocialInsuranceApplicationDto;
import jp.asatex.quickapp.kyuyokeisan_backend_service.domain.SocialInsuranceDomainService;
import jp.asatex.quickapp.kyuyokeisan_backend_service.domain.dto.SocialInsuranceDomainDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 社会保险 Application Service
 * 提供应用层服务，调用 Domain 层进行业务处理
 * 采用流式编程风格
 */
@Service
public class SocialInsuranceApplicationService {

    private final SocialInsuranceDomainService socialInsuranceDomainService;

    public SocialInsuranceApplicationService(SocialInsuranceDomainService socialInsuranceDomainService) {
        this.socialInsuranceDomainService = socialInsuranceDomainService;
    }

    /**
     * 查询社会保险金额
     * 调用 Domain 层的同名方法获取数据，并转换为 Application DTO
     *
     * @param monthlySalary 月薪
     * @param age 年龄
     * @return Mono<SocialInsuranceApplicationDto> 社会保险金额 Application DTO
     */
    public Mono<SocialInsuranceApplicationDto> socialInsuranceQuery(Integer monthlySalary, Integer age) {
        return socialInsuranceDomainService.socialInsuranceQuery(monthlySalary, age)
                .map(this::convertSocialInsuranceToApplicationDto);
    }

    /**
     * 将 Domain DTO 转换为 Application DTO（社会保险）
     * 采用流式编程风格进行数据转换
     *
     * @param domainDto Domain DTO
     * @return Application DTO
     */
    private SocialInsuranceApplicationDto convertSocialInsuranceToApplicationDto(SocialInsuranceDomainDto domainDto) {
        // 转换雇员费用结构体
        SocialInsuranceDomainDto.EmployeeCost domainEmployeeCost = domainDto.getEmployeeCost();
        SocialInsuranceApplicationDto.EmployeeCost applicationEmployeeCost = 
                SocialInsuranceApplicationDto.EmployeeCost.builder()
                        .healthCostWithNoCare(domainEmployeeCost.getHealthCostWithNoCare())
                        .careCost(domainEmployeeCost.getCareCost())
                        .pension(domainEmployeeCost.getPension())
                        .build();

        // 转换雇主费用结构体
        SocialInsuranceDomainDto.EmployerCost domainEmployerCost = domainDto.getEmployerCost();
        SocialInsuranceApplicationDto.EmployerCost applicationEmployerCost = 
                SocialInsuranceApplicationDto.EmployerCost.builder()
                        .healthCostWithNoCare(domainEmployerCost.getHealthCostWithNoCare())
                        .careCost(domainEmployerCost.getCareCost())
                        .pension(domainEmployerCost.getPension())
                        .build();

        // 构建应用层 DTO
        return SocialInsuranceApplicationDto.builder()
                .employeeCost(applicationEmployeeCost)
                .employerCost(applicationEmployerCost)
                .build();
    }
}

