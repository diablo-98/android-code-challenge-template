@file:OptIn(ExperimentalCoroutinesApi::class)

package com.wassha.androidcodechallenge.data.data

import com.wassha.androidcodechallenge.data.JokeRepository
import com.wassha.androidcodechallenge.data.JokeService
import com.wassha.androidcodechallenge.data.JokeStatus
import com.wassha.androidcodechallenge.db.dao.JokeDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException

@ExtendWith(MockKExtension::class)
@MockKExtension.ConfirmVerification
@MockKExtension.CheckUnnecessaryStub
class JokeRepositoryTest {

    @MockK
    private lateinit var api: JokeService
    @MockK(relaxUnitFun = true)
    private lateinit var dao: JokeDao

    // System under test
    private lateinit var repo: JokeRepository


    @BeforeEach
    fun before() {
        repo = JokeRepository(api, dao)
    }

    @Nested
    inner class FetchJokeTests {

        @Nested
        inner class Success {

            @Test
            fun success() = runTest {
                // Given
                val joke = "Hello world!"
                val jokeJson = """
                    {
                        "joke":"$joke"
                    }   
                """.trimIndent()
                val response = mockk<Response<String>> {
                    every { isSuccessful } returns true
                    every { body() } returns jokeJson
                }

                mockkStatic(Call<String>::awaitResponse)
                val call = mockk<Call<String>> {
                    coEvery { awaitResponse() } returns response
                }

                every { api.getSingleProgrammingJoke() } returns call

                //When
                repo.fetchJoke()

                // Then:
                // 1. Ensure joke is inserted into the DB
                coVerify(exactly = 1) {
                    dao.insert(
                        withArg {
                            assertEquals(0, it.id)
                            assertEquals(joke, it.content)
                            assertEquals(JokeStatus.Online, it.status)
                        }
                    )
                }
                // 2. Ensure that the setStatus is not invoked
                coVerify(exactly = 0) {
                    dao.setStatus(any())
                }
            }
        }

        @Nested
        inner class Failure {

            @Test
            fun failureDueToIsSuccessfulReturningFalse() = runTest {
                // Given
                val response = mockk<Response<String>> {
                    every { isSuccessful } returns false
                }

                mockkStatic(Call<String>::awaitResponse)
                val call = mockk<Call<String>> {
                    coEvery { awaitResponse() } returns response
                }

                every { api.getSingleProgrammingJoke() } returns call

                // When
                repo.fetchJoke()

                // Then
                verifyFailure()
            }

            @Test
            fun failureDueToExceptionFromAwaitResponse() = runTest {
                // Given
                mockkStatic(Call<String>::awaitResponse)
                val call = mockk<Call<String>> {
                    coEvery { awaitResponse() } throws IOException("Example error")
                }

                every { api.getSingleProgrammingJoke() } returns call

                // When
                repo.fetchJoke()

                // Then
                verifyFailure()
            }

            private fun verifyFailure() {
                // 1. Ensure that the setStatus is invoked with status 'Offline'
                coVerify(exactly = 1) {
                    dao.setStatus(
                        withArg {
                            assertEquals(JokeStatus.Offline, it)
                        }
                    )
                }
                // 2. Ensure new joke is NOT inserted into the DB
                coVerify(exactly = 0) {
                    dao.insert(any())
                }
            }
        }
    }

}