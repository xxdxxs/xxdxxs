package com.xxdxxs.annotate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描路径配置类
 * @author xxdxxs
 */
@Configuration
@ComponentScan(basePackages = "com.xxdxxs.utils")
public class BasePointAspectConfig {

}
