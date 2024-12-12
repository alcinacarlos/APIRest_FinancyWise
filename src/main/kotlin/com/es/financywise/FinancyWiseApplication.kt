package com.es.financywise

import com.es.financywise.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class FinancyWiseApplication

fun main(args: Array<String>) {
	runApplication<FinancyWiseApplication>(*args)
}
