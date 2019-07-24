package api.controllers

import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}
import api.dtos.FileDTO
import api.utils.DateUtils.stringToDateFormat
import api.utils.{FakeUUIDGenerator, UUIDGenerator}
import database.repositories.file.FileRepository
import database.repositories.task.{FakeTaskRepository, TaskRepository}
import executionengine.{ExecutionManager, FakeExecutionManager}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, MustMatchers}
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.Injector
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc.{ControllerComponents, Results}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.{ExecutionContext, Future}

//TODO implements missing tests
class FileControllerSuite extends PlaySpec with Results with GuiceOneAppPerSuite with BeforeAndAfterAll with BeforeAndAfterEach with MustMatchers with MockitoSugar {

  private lazy val appBuilder: GuiceApplicationBuilder = new GuiceApplicationBuilder()
  private lazy val injector: Injector = appBuilder.injector()
  private implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  implicit val fileRepo: FileRepository = mock[FileRepository]
  private implicit val taskRepo: TaskRepository = new FakeTaskRepository
  private implicit val UUIDGen: UUIDGenerator = new FakeUUIDGenerator
  private implicit val executionManager: ExecutionManager = new FakeExecutionManager
  private val cc: ControllerComponents = injector.instanceOf[ControllerComponents]
  private implicit val actorSystem: ActorSystem = ActorSystem()
  private implicit val mat: Materializer = ActorMaterializer()

  val file1 = FileDTO("asd1", "test1", stringToDateFormat("2018-02-01 12:00:00", "yyyy-MM-dd HH:mm:ss"))
  val file2 = FileDTO("asd2", "test2", stringToDateFormat("2018-03-01 12:00:00", "yyyy-MM-dd HH:mm:ss"))
  val file3 = FileDTO("asd3", "test3", stringToDateFormat("2018-02-01 12:00:00", "yyyy-MM-dd HH:mm:ss"))

  val seqFiles: Seq[FileDTO] = Seq(file1, file2, file3)

  when(fileRepo.selectAllFiles).thenReturn(Future.successful(seqFiles))

  "FileController#getAllFiles" should {
    "receive a GET request" in {
      val fakeRequest = FakeRequest(GET, s"/file")
        .withHeaders(HOST -> "localhost:9000")
      val fileController = new FileController(cc)
      val result = fileController.getAllFiles.apply(fakeRequest)

      status(result) mustBe OK
      contentAsJson(result) mustBe Json.toJson(seqFiles)
    }
  }

  when(fileRepo.selectFileById("asd1")).thenReturn(Future.successful(Some(file1)))

  "FileController#getFileById" should {
    "receive a GET request." in {
      val id = "asd1"
      val fakeRequest = FakeRequest(GET, s"/file/" + id)
        .withHeaders(HOST -> "localhost:9000")
      val fileController = new FileController(cc)
      val result = fileController.getFileById(id).apply(fakeRequest)

      status(result) mustBe OK
      contentAsJson(result) mustBe Json.toJson(file1)
    }
  }

  when(fileRepo.deleteFileById("asd1")).thenReturn(Future.successful(1))

  "FileController#deleteFile" should {
    "receive a DELETE request." in {
      val id = "asd1"
      val fakeRequest = FakeRequest(DELETE, s"/file/" + id)
        .withHeaders(HOST -> "localhost:9000")
      val fileController = new FileController(cc)
      val result = fileController.deleteFile(id).apply(fakeRequest)

      status(result) mustBe NO_CONTENT
    }
  }

}
