package jp.asatex.quickapp.kyuyokeisan_backend_service.repository;

import jp.asatex.quickapp.kyuyokeisan_backend_service.bean.entity.PremiumBracket;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * 社会保险 Repository 接口
 * 用于查询保险费等级信息
 * 使用 R2DBC 响应式编程风格
 */
@Repository
public interface SocialInsuranceRepository extends 
        ReactiveCrudRepository<PremiumBracket, Long>,
        ReactiveSortingRepository<PremiumBracket, Long> {

    /**
     * 根据金额范围查找对应的保险费等级
     * 查找 min_amount <= amount < max_amount 的记录
     *
     * @param amount 金额
     * @return Mono<PremiumBracket> 保险费等级信息
     */
    @Query("SELECT * FROM premium_bracket WHERE $1 >= min_amount AND $1 < max_amount LIMIT 1")
    Mono<PremiumBracket> findByAmount(Integer amount);
}

