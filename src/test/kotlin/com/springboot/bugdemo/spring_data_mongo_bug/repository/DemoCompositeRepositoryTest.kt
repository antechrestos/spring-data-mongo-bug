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
class DemoCompositeRepositoryTest {

    @Autowired
    private lateinit var demoCompositeRepository: DemoCompositeRepository

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
        demoCompositeRepository.deleteAll()
    }

    @Test
    fun `it should allow save`(): Unit = runBlocking {
        demoCompositeRepository.save(
            DemoComposite(
                DemoCompositeId(
                    user = User("tenant", "1"),
                    demoId = "demo-id",
                    timestamp = Instant.now().minusSeconds(2),
                ),
                comment = "First for user 1"
            )
        )
    }

    @Test
    fun `it should allow saveAll`(): Unit = runBlocking {
        demoCompositeRepository.saveAll(
            listOf(
                DemoComposite(
                    DemoCompositeId(
                        user = User("tenant", "1"),
                        demoId = "demo-id",
                        timestamp = Instant.now().minusSeconds(2),
                    ),
                    comment = "First for user 1"
                ),
                DemoComposite(
                    DemoCompositeId(
                        user = User("tenant", "1"),
                        demoId = "demo-id",
                        timestamp = Instant.now().minusSeconds(1),
                    ),
                    comment = "Second for user 1",
                ),
                DemoComposite(
                    DemoCompositeId(
                        user = User("tenant", "1"),
                        demoId = "demo-id",
                        timestamp = Instant.now(),
                    ),
                    comment = "Third for user 1"
                ),
                DemoComposite(
                    DemoCompositeId(
                        user = User("tenant", "2"),
                        demoId = "demo-id",
                        timestamp = Instant.now(),
                    ),
                    comment = "First for user 1"
                ),
            )
        ).toList()
        assertThat(demoCompositeRepository.count()).isEqualTo(4)
    }
}