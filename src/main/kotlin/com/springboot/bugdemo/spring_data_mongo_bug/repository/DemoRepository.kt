package com.springboot.bugdemo.spring_data_mongo_bug.repository

import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

data class Demo(@Id val id:String, val comment: String)

interface DemoRepository: CoroutineCrudRepository<Demo, String>
