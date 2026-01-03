package jp.asatex.quickapp.kyuyokeisan_backend_service.domain.impl;

import jp.asatex.quickapp.kyuyokeisan_backend_service.bean.entity.PremiumBracket;
import jp.asatex.quickapp.kyuyokeisan_backend_service.domain.SocialInsuranceDomainService;
import jp.asatex.quickapp.kyuyokeisan_backend_service.domain.dto.SocialInsuranceDomainDto;
import jp.asatex.quickapp.kyuyokeisan_backend_service.repository.SocialInsuranceRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 社会保险 Domain Service 实现类
 * 提供社会保险业务逻辑处理和流式编程风格的操作方法
 */
@Service
public class SocialInsuranceDomainServiceImpl implements SocialInsuranceDomainService {

    private final SocialInsuranceRepository socialInsuranceRepository;

    public SocialInsuranceDomainServiceImpl(SocialInsuranceRepository socialInsuranceRepository) {
        this.socialInsuranceRepository = socialInsuranceRepository;
    }

    @Override
    public Mono<SocialInsuranceDomainDto> socialInsuranceQuery(Integer monthlySalary, Integer age) {
        return Mono.just(monthlySalary)
                .flatMap(salary -> socialInsuranceRepository.findByAmount(salary))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        String.format("未找到月薪 %d 对应的保险费等级", monthlySalary))))
                .flatMap(bracket -> calculateSocialInsurance(bracket, age));
    }

    private Mono<SocialInsuranceDomainDto> calculateSocialInsurance(PremiumBracket bracket, Integer age) {
        return Mono.just(bracket)
                .map(b -> {
                    BigDecimal healthCostWithNoCare = b.getHealthNoCare();
                    BigDecimal healthCostWithCare = b.getHealthCare();
                    BigDecimal pension = b.getPension();

                    // 计算介护保险金额
                    BigDecimal careCost = BigDecimal.ZERO;
                    if (age != null && age >= 40) {
                        careCost = healthCostWithCare.subtract(healthCostWithNoCare);
                    }

                    // 费用分配比例：雇员和雇主各承担50%
                    BigDecimal half = new BigDecimal("0.5");
                    int scale = 2;

                    // 计算雇员承担的费用（50%）
                    BigDecimal employeeHealthCostWithNoCare = healthCostWithNoCare.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employeeCareCost = careCost.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employeePension = pension.multiply(half).setScale(scale, RoundingMode.HALF_UP);

                    // 计算雇主承担的费用（50%）
                    BigDecimal employerHealthCostWithNoCare = healthCostWithNoCare.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employerCareCost = careCost.multiply(half).setScale(scale, RoundingMode.HALF_UP);
                    BigDecimal employerPension = pension.multiply(half).setScale(scale, RoundingMode.HALF_UP);

                    // 构建雇员费用结构体
                    SocialInsuranceDomainDto.EmployeeCost employeeCost = SocialInsuranceDomainDto.EmployeeCost.builder()
                            .healthCostWithNoCare(employeeHealthCostWithNoCare)
                            .careCost(employeeCareCost)
                            .pension(employeePension)
                            .build();

                    // 构建雇主费用结构体
                    SocialInsuranceDomainDto.EmployerCost employerCost = SocialInsuranceDomainDto.EmployerCost.builder()
                            .healthCostWithNoCare(employerHealthCostWithNoCare)
                            .careCost(employerCareCost)
                            .pension(employerPension)
                            .build();

                    // 使用 Builder 模式构建 DTO
                    return SocialInsuranceDomainDto.builder()
                            .employeeCost(employeeCost)
                            .employerCost(employerCost)
                            .build();
                });
    }
}

