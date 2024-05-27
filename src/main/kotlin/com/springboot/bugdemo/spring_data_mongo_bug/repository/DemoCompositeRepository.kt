package com.springboot.bugdemo.spring_data_mongo_bug.repository

import com.springboot.bugdemo.spring_data_mongo_bug.repository.DemoComposite.Companion.COLLECTION_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

data class DemoCompositeId(val user: User,
                           val demoId: String,
                           val timestamp: Instant,)

@Document(collection = COLLECTION_NAME)
data class DemoComposite(
    @Id val id: DemoCompositeId,
    val comment: String,
) {
    companion object {
        const val COLLECTION_NAME = "demoComposite"
    }
}

interface DemoCompositeRepository: CoroutineCrudRepository<DemoComposite, DemoCompositeId>