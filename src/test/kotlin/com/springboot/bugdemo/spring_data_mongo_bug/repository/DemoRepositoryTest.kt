package com.springboot.bugdemo.spring_data_mongo_bug.repository

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.time.Instant

@SpringBootTest
@Testcontainers
class DemoRepositoryTest {

    @Autowired
    private lateinit var demoRepository: DemoRepository

    @TestConfiguration(proxyBeanMethods = false)
    class TestSpringDataMongoBugApplication {

        @Bean
        @ServiceConnection
        fun mongoDbContainer(): MongoDBContainer {
            return MongoDBContainer(DockerImageName.parse("mongo:5.0.9"))
                .withReuse(true)
        }

    }

    @BeforeEach
    fun clean(): Unit = runBlocking{
        demoRepository.deleteAll()
    }

    @Test
    fun `it should allow save`(): Unit = runBlocking {
        demoRepository.save(
            Demo(
                id = "1",
                comment = "First for user 1"
            )
        )
    }

    @Test
    fun `it should allow saveAll`(): Unit = runBlocking {
        demoRepository.saveAll(
            listOf(
                Demo(
                    id = "1",
                    comment = "First for user 1"
                ),
                Demo(
                    id = "2",
                    comment = "First for user 2"
                )
            )
        ).toList()
        assertThat(demoRepository.count()).isEqualTo(2)
    }
}