package com.springboot.bugdemo.spring_data_mongo_bug

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class SpringDataMongoBugApplication

fun main(args: Array<String>) {
	runApplication<SpringDataMongoBugApplication>(*args)
}
