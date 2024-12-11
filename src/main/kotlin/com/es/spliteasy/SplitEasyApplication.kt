package com.es.spliteasy

import com.es.spliteasy.security.RSAKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class SplitEasyApplication

fun main(args: Array<String>) {
	runApplication<SplitEasyApplication>(*args)
}
