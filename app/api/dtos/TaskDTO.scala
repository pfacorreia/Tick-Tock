package api.dtos

import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}
import database.utils.DatabaseUtils._
import akka.japi
import api.validators.Error
import slick.jdbc.MySQLProfile.api._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import api.validators.Error._
import api.utils.DateUtils._
import database.repositories.slick.FileRepositoryImpl
import database.utils.DatabaseUtils

import scala.concurrent.{ExecutionContext, Future}

/**
  * Data transfer object for the scheduled tasks on the service side.
  * @param startDateAndTime Date and time of when the task is executed.
  * @param taskName Name of the file that is executed.
  */
case class TaskDTO(
                    startDateAndTime: Date,
                    fileName: String
                  )

/**
  * Companion object for the TaskDTO
  */
object TaskDTO {

  implicit val ec = ExecutionContext.global
  val fileRepo = new FileRepositoryImpl(DEFAULT_DB)

  /**
    * Method that constructs the TaskDTO giving strings as dates and making the date format validation and conversion from string to date.
    * @param startDateAndTime Date and time of when the task is executed in a String format.
    * @param fileName Name of the file that is executed.
    * @return the taskDTO if the date received is valid. Throws an IllegalArgumentException if it's invalid.
    */
  def construct(startDateAndTime: String, fileName: String): TaskDTO = {
    val date = getValidDate(startDateAndTime)
    TaskDTO(date.get, fileName)
  }

  /**
    * Implicit that defines how a TaskDTO is read from the JSON request.
    * This implicit is used on the TaskController when Play's "validate" method is called.
    */
  implicit val taskReads: Reads[TaskDTO] = (
    (JsPath \ "startDateAndTime").read[Date] and
      (JsPath \ "fileName").read[String]
    ) (TaskDTO.apply _)

  /**
    * Implicit that defines how a TaskDTO is written to a JSON format.
    */
  implicit val taskFormat: OWrites[TaskDTO] = Json.writes[TaskDTO]

}
