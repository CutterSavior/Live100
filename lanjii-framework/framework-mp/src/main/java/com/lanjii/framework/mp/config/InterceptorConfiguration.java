package com.lanjii.framework.mp.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截器配置
 *
 * @author lanjii
 */
@Configuration
public class InterceptorConfiguration {

    /**
     * 防止全表更新与删除插件
     */
    @Bean
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor() {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 插件配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(
            ObjectProvider<TenantLineInnerInterceptor> tenantInterceptorProvider,
            BlockAttackInnerInterceptor blockAttack) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 多租户插件
        tenantInterceptorProvider.ifAvailable(interceptor::addInnerInterceptor);
        // 防止全表更新与删除插件
        interceptor.addInnerInterceptor(blockAttack);
        return interceptor;
    }

    /**
     * 分页插件
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor();
    }
}
