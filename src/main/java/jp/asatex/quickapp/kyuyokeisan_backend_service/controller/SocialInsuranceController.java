package jp.asatex.quickapp.kyuyokeisan_backend_service.controller;

import jp.asatex.quickapp.kyuyokeisan_backend_service.application.SocialInsuranceApplicationService;
import jp.asatex.quickapp.kyuyokeisan_backend_service.application.dto.SocialInsuranceApplicationDto;
import jp.asatex.quickapp.kyuyokeisan_backend_service.controller.dto.SocialInsuranceDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 社会保险 Controller
 * 提供 RESTful API 接口，采用 WebFlux 响应式编程风格
 */
@RestController
@RequestMapping("/")
@CrossOrigin
public class SocialInsuranceController {

    private final SocialInsuranceApplicationService socialInsuranceApplicationService;

    public SocialInsuranceController(SocialInsuranceApplicationService socialInsuranceApplicationService) {
        this.socialInsuranceApplicationService = socialInsuranceApplicationService;
    }

    /**
     * 查询社会保险金额
     * GET /socialInsuranceQuery?monthlySalary=650000&age=35
     *
     * @param monthlySalary 月薪（1円～10,000,000円）
     * @param age 年龄（1歳～120歳）
     * @return Mono<SocialInsuranceDto> 社会保险金额 DTO
     */
    @GetMapping("/socialInsuranceQuery")
    public Mono<SocialInsuranceDto> socialInsuranceQuery(
            @RequestParam("monthlySalary") 
            @NotNull(message = "月薪不能为空")
            @Min(value = 1, message = "月薪必须大于0")
            @Max(value = 10000000, message = "月薪不能超过10,000,000円")
            Integer monthlySalary,
            @RequestParam("age") 
            @NotNull(message = "年龄不能为空")
            @Min(value = 1, message = "年龄必须大于0")
            @Max(value = 120, message = "年龄不能超过120歳")
            Integer age) {
        // 额外验证：确保参数在合理范围内
        if (monthlySalary == null || monthlySalary <= 0 || monthlySalary > 10000000) {
            return Mono.error(new IllegalArgumentException("月薪必须在1円到10,000,000円之间"));
        }
        if (age == null || age <= 0 || age > 120) {
            return Mono.error(new IllegalArgumentException("年龄必须在1歳到120歳之间"));
        }
        return socialInsuranceApplicationService.socialInsuranceQuery(monthlySalary, age)
                .map(this::convertToDto);
    }

    /**
     * 将 Application DTO 转换为 Controller DTO
     * 采用流式编程风格进行数据转换
     *
     * @param applicationDto Application DTO
     * @return Controller DTO
     */
    private SocialInsuranceDto convertToDto(SocialInsuranceApplicationDto applicationDto) {
        // 转换雇员费用结构体
        SocialInsuranceApplicationDto.EmployeeCost applicationEmployeeCost = applicationDto.getEmployeeCost();
        SocialInsuranceDto.EmployeeCost controllerEmployeeCost = SocialInsuranceDto.EmployeeCost.builder()
                .healthCostWithNoCare(applicationEmployeeCost.getHealthCostWithNoCare())
                .careCost(applicationEmployeeCost.getCareCost())
                .pension(applicationEmployeeCost.getPension())
                .build();

        // 转换雇主费用结构体
        SocialInsuranceApplicationDto.EmployerCost applicationEmployerCost = applicationDto.getEmployerCost();
        SocialInsuranceDto.EmployerCost controllerEmployerCost = SocialInsuranceDto.EmployerCost.builder()
                .healthCostWithNoCare(applicationEmployerCost.getHealthCostWithNoCare())
                .careCost(applicationEmployerCost.getCareCost())
                .pension(applicationEmployerCost.getPension())
                .build();

        // 构建控制层 DTO
        return SocialInsuranceDto.builder()
                .employeeCost(controllerEmployeeCost)
                .employerCost(controllerEmployerCost)
                .build();
    }
}

